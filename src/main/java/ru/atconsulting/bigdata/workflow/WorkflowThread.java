package ru.atconsulting.bigdata.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.configuration.RestClientConfiguration;
import ru.atconsulting.bigdata.domain.answer.Answer;
import ru.atconsulting.bigdata.domain.answer.ErrorAnswer;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;
import ru.atconsulting.bigdata.domain.answer.StepAnswer;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.events.ShutdownEventPublisher;
import ru.atconsulting.bigdata.service.CurrentStepService;
import ru.atconsulting.bigdata.service.StepResultsService;
import ru.atconsulting.bigdata.workflow.actions.*;
import ru.atconsulting.bigdata.workflow.xml.business.Flow;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

import java.util.List;

/**
 * Created by NSkovpin on 14.06.2016.
 */
@Component
@Scope("prototype")
public class WorkflowThread extends Thread {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ShutdownEventPublisher shutdownEventPublisher;

    @Autowired
    private ThreadMap threadMap;

    @Autowired
    private LoggerThread loggerThread;

    @Autowired
    private CurrentStepService currentStepService;

    @Autowired
    private StepResultsService stepResultsService;

    @Autowired
    @Qualifier("telegramClient")
    private RestClient telegramClient;

    private Flow flow;

    private ClusterAnswer clusterAnswer;

    public WorkflowThread(Flow flow, ClusterAnswer clusterAnswer) {
        this.flow = flow;
        this.clusterAnswer = clusterAnswer;
    }

    @Override
    public void run() {
        List<Step> orderedStepList = flow.getOrderedSteps();

        Step interruptedStep = null;

        for (Step step : orderedStepList) {

            try {
                Action action = ActionEnum.getActionByName(step, clusterAnswer, applicationContext);

                if (interruptedStep != null && step != interruptedStep) {
                    continue;
                } else {
                    interruptedStep = null;
                }

                Object actionResult = action.run();

                if (action instanceof CheckCurrentStepAction && !((String) actionResult).isEmpty()) {
                    interruptedStep = flow.getReloadStepByID(flow.getStep(), (String) actionResult);
                    if (!interruptedStep.getId().equals(step.getId()))
                        continue;
                } else if (action instanceof GlassfishAction && actionResult == null) {
                    glassfishLoop:
                    for (int i = 0; i < 3; i++) {
                        actionResult = action.run();
                        if (actionResult != null) {
                            break glassfishLoop;
                        }
                    }
                    if (actionResult == null) {
                        loggerThread.addLog("Glassfish failed 3 times!", WorkflowThread.class);
                        throw new RuntimeException("Glassfish failed 3 times");
                    }
                }


                StepAnswer<Object> stepAnswer = new StepAnswer<>();
                stepAnswer.setStep(step.getId());
                stepAnswer.setAction(step.getExecute().getName());
                stepAnswer.setTelegramId(clusterAnswer.getUserInfo().getTelegramId());
                stepAnswer.setInfo(actionResult);
                clusterAnswer.setStepAnswer(stepAnswer);

                sendStepResult(clusterAnswer);
                saveStepResults(step, stepAnswer);

                interruptedStep = null;
            } catch (Exception e) {
                e.printStackTrace();
                loggerThread.addLog("Action error!" + "Step id:" + step.getId() +
                        " Action name:" + step.getExecute().getName() +
                        " Info:" + e.getMessage(), WorkflowThread.class);
                ErrorAnswer errorAnswer = new ErrorAnswer();
                errorAnswer.setAction("Error Action");
                errorAnswer.setError(e.getMessage());
                errorAnswer.setStep(step.getId());
                errorAnswer.setTelegramId(clusterAnswer.getUserInfo().getTelegramId());
                clusterAnswer.setErrorAnswer(errorAnswer);
                Step errorStep = flow.getStepByID(flow.getStep(), step.getError().getTo());
                try {
                    Action errorAction = ActionEnum.getActionByName(errorStep, clusterAnswer, applicationContext);
                    Object errorResponse = errorAction.run();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    loggerThread.addLog("Error while sending errorAction!" + e1.getMessage(), WorkflowThread.class);
                }

                shutdownEventPublisher.publishShutdown(clusterAnswer.getUserInfo().getTelegramId(),
                        clusterAnswer.getUserInfo().getTelegramId());

                saveStepResults(step, errorAnswer);

                threadMap.stopAndRemoveThread(clusterAnswer.getUserInfo().getTelegramId());
                return;
            }
        }
        loggerThread.addLog("Successfully processed telegramId:" + clusterAnswer.getUserInfo().getTelegramId(), WorkflowThread.class);
        currentStepService.removeCurrentStep(clusterAnswer.getUserInfo().getTelegramId());
        loggerThread.addLog("Thread finished for telegramId:" + clusterAnswer.getWorkflow(), WorkflowThread.class);
    }

    private void saveStepResults(Step step, Answer stepAnswer) {

        if (!(stepAnswer instanceof ErrorAnswer))
            currentStepService.increaseCurrentStep(clusterAnswer.getUserInfo().getTelegramId(),
                    clusterAnswer.getUserInfo().getSubscriberNo(),
                    clusterAnswer.getWorkflow().getFlowId(),
                    step
            );

        CurrentStep currentStep = currentStepService.findByTelegramIdAndCtnAndFlow(clusterAnswer.getUserInfo().getTelegramId(),
                clusterAnswer.getUserInfo().getSubscriberNo(),
                clusterAnswer.getWorkflow().getFlowId());

        stepResultsService.addStepResult(stepAnswer, step, currentStep);
    }

    private void sendStepResult(ResourceSupport answer) {
        ClusterAnswer clusterAnswer = telegramClient.getRestTemplate().postForObject(telegramClient.getClientProperties()
                .getProperty(RestClientConfiguration.TelegramClientPropertyNames.STEP_RESULT_URL.getValue()), answer, ClusterAnswer.class);
    }


}
