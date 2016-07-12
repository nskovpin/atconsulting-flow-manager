package ru.atconsulting.bigdata.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;
import ru.atconsulting.bigdata.domain.answer.ErrorAnswer;
import ru.atconsulting.bigdata.domain.flow.Workflow;
import ru.atconsulting.bigdata.domain.user.UserInfo;
import ru.atconsulting.bigdata.workflow.LoggerThread;
import ru.atconsulting.bigdata.workflow.ThreadMap;
import ru.atconsulting.bigdata.workflow.WorkflowThread;
import ru.atconsulting.bigdata.workflow.xml.FlowHolder;
import ru.atconsulting.bigdata.workflow.xml.business.Flow;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@RestController
@RequestMapping("/rest/workflow")
@Scope("request")
//@Secured("ADMIN")
public class SearchWorkflowRestController {

    private static final Logger logger = Logger.getLogger(SearchWorkflowRestController.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LoggerThread loggerThread;

    @Autowired
    private FlowHolder flowHolder;

    @Autowired
    private ThreadMap threadMap;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<ClusterAnswer> getWorkflow(@RequestParam(value = "telegramId", required = true) String telegramId,
                                                     @RequestParam(value = "subscriberNo", required = false) String subscriberNo,
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "surname", required = false) String surname,
                                                     @RequestParam(value = "flowId", required = true) String flowId,
                                                     @RequestParam(value = "flowName", required = false) String flowName,
                                                     @RequestParam(value = "flowAdditionalInfo", required = false) String flowAdditionalInfo) {
        logger.info("Searching for workflow for " + telegramId + ";" + flowId);

        UserInfo userInfo = new UserInfo();
        userInfo.setTelegramId(telegramId);
        userInfo.setSubscriberNo(subscriberNo);
        userInfo.setName(name);
        userInfo.setSurname(surname);

        Workflow workflow = new Workflow();
        workflow.setFlowId(flowId);
        workflow.setFlowName(flowName);
        workflow.setFlowAdditionalInfo(flowAdditionalInfo);

        ClusterAnswer clusterAnswer = new ClusterAnswer();
        clusterAnswer.setUserInfo(userInfo);
        clusterAnswer.setWorkflow(workflow);
        clusterAnswer.add(linkTo(methodOn(SearchWorkflowRestController.class)
                .getWorkflow(telegramId, subscriberNo, name, surname, flowId, flowName, flowAdditionalInfo)).withSelfRel());

        Flow flow = flowHolder.getFlowById(workflow.getFlowId());
        appendToClusterAnswer(clusterAnswer, flow);
        return new ResponseEntity<>(clusterAnswer, HttpStatus.OK);

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<ClusterAnswer> processRequestJson(@RequestBody ClusterAnswer clusterAnswer) {
        if (clusterAnswer.getUserInfo().getTelegramId() == null ||
                clusterAnswer.getUserInfo().getSubscriberNo() == null ||
                clusterAnswer.getWorkflow().getFlowId() == null) {
            loggerThread.addLog("TelegramId or ctn or flowId are null!", SearchWorkflowRestController.class);
            throw new NullPointerException("TelegramId or ctn or flowId are null!");
        }
        Flow flow = flowHolder.getFlowById(clusterAnswer.getWorkflow().getFlowId());
        appendToClusterAnswer(clusterAnswer, flow);
        return new ResponseEntity<>(clusterAnswer, HttpStatus.OK);
    }

    private void startProcess(Flow flow, ClusterAnswer clusterAnswer) {
        WorkflowThread workflowThread = applicationContext.getBean(WorkflowThread.class, flow, clusterAnswer);
        threadMap.addElement(clusterAnswer.getUserInfo().getTelegramId(), workflowThread);
        workflowThread.start();
        loggerThread.addLog("Started thread for user:" + clusterAnswer.getUserInfo(), SearchWorkflowRestController.class);
    }

    private void appendToClusterAnswer(ClusterAnswer clusterAnswer, Flow flow){
        if(flow != null){

            startProcess(flow, clusterAnswer);
            clusterAnswer.setStatus("Processing");
            loggerThread.addLog("Processing " + clusterAnswer.getUserInfo().getTelegramId(), SearchWorkflowRestController.class);
        }else{
            clusterAnswer.setStatus("Error");
            ErrorAnswer errorAnswer = new ErrorAnswer();
            errorAnswer.setError("Can't find flowId:"+clusterAnswer.getWorkflow().getFlowId());
            errorAnswer.setTelegramId(clusterAnswer.getUserInfo().getTelegramId());
            clusterAnswer.setErrorAnswer(errorAnswer);
            loggerThread.addLog("Error! Can't find flowId:"+clusterAnswer.getWorkflow().getFlowId() +" TelegramId:" + clusterAnswer.getUserInfo().getTelegramId(), SearchWorkflowRestController.class);
        }
    }


}
