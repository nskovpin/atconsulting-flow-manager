package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.service.CurrentStepService;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@Component("checkCurrentStepAction")
@Scope("prototype")
public class CheckCurrentStepAction implements Action {

    @Autowired
    private CurrentStepService currentStepService;

    private Step step;

    private String telegramId;

    private String ctn;

    private String flow;

    public CheckCurrentStepAction(Step step, String telegramId, String ctn, String flow){
        this.step = step;
        this.telegramId = telegramId;
        this.ctn = ctn;
        this.flow = flow;
    }

    @Override
    public Object run() {
        CurrentStep currentStep = currentStepService.findByTelegramIdAndCtnAndFlow(telegramId, ctn, flow);
        String step = "";
        if(currentStep != null){
            step = currentStep.getCurrentStep();
        }
        return step;
    }

    @Override
    public Step getStep() {
        return step;
    }

}
