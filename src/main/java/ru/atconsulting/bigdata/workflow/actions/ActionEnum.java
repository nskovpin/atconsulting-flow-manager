package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.context.ApplicationContext;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 20.06.2016.
 */
public enum ActionEnum {
    GLASSFISH_instruction,
    CHECK_CURRENT_STEP_instruction,
    RETURN_RESULT_instruction,
    ERROR_instruction,
    TSMS_instruction;

    public static Action getActionByName(Step step, ClusterAnswer clusterAnswer, ApplicationContext applicationContext) throws Exception{
        ActionEnum actionEnum = ActionEnum.valueOf(step.getExecutorMappingInstruction().getName());
        switch (actionEnum){
            case GLASSFISH_instruction:
                return applicationContext.getBean(GlassfishAction.class, step, clusterAnswer.getUserInfo().getSubscriberNo(),
                        step.getExecutorMappingInstruction().getParams().getArgs().getValue());
            case CHECK_CURRENT_STEP_instruction:
                return applicationContext.getBean(CheckCurrentStepAction.class,
                        step, clusterAnswer.getUserInfo().getTelegramId(), clusterAnswer.getUserInfo().getSubscriberNo(), clusterAnswer.getWorkflow().getFlowId());
            case RETURN_RESULT_instruction:
                return applicationContext.getBean(ReturnResultAction.class, step, clusterAnswer);
            case ERROR_instruction:
                return applicationContext.getBean(ErrorAction.class, step, clusterAnswer.getUserInfo().getTelegramId(),
                        clusterAnswer.getUserInfo().getSubscriberNo(), clusterAnswer.getWorkflow().getFlowId(), clusterAnswer);
            case TSMS_instruction:
                return applicationContext.getBean(TsmsAction.class, step, clusterAnswer.getUserInfo().getSubscriberNo());
        }
        throw new RuntimeException("Application doesn't know this action:"
                + step.getExecute().getName()
        );
    }

}
