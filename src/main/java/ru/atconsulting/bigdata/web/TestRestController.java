package ru.atconsulting.bigdata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.atconsulting.bigdata.domain.Hello;
import ru.atconsulting.bigdata.domain.Shutdown;
import ru.atconsulting.bigdata.domain.answer.*;
import ru.atconsulting.bigdata.domain.flow.Workflow;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.domain.user.UserInfo;
import ru.atconsulting.bigdata.repo.CurrentStepRepository;
import ru.atconsulting.bigdata.service.StepResultsService;
import ru.atconsulting.bigdata.workflow.LoggerThread;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@RestController
public class TestRestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StepResultsService stepResultsService;

    @Qualifier("currentStepRepository")
    @Autowired
    private CurrentStepRepository currentStepRepository;

    @Autowired
    private LoggerThread loggerThread;

    @RequestMapping(value = "/hello")
    public String sayHello(){
        return "<h5>Welcome!</h5>" +
                "This is ATC server for communication with telegram bot.<br>" +
                "Available links:<br>/workflowTest<br> /workflow/search<br> /workflow/shutdown<br><br>" +
                "You can copy this: /workflow/search?telegramId=123&subscriberNo=951844&name=Nikolay&surname=Skovpin&flowId=3";
    }

    @RequestMapping(value = "/")
    public ResponseEntity<Hello> sayWelcome(){
        Hello hello = new Hello();
        hello.setInformation("Welcome! This is ATC server for communication with telegram bot. " +
                "Available links:   /workflowTest   /rest/workflow/search    /rest/workflow/shutdown");
        hello.add(linkTo(SearchWorkflowRestController.class).slash("search").withRel("workflow.search"));
        hello.add(linkTo(SearchWorkflowRestController.class).slash("shutdown").withRel("workflow.shutdown"));
        return new ResponseEntity<>(hello, HttpStatus.OK);
    }


    @RequestMapping(value = "/workflowTest")
    public ResponseEntity<ClusterAnswer> getWorkflow(){
        ClusterAnswer clusterAnswer = new ClusterAnswer();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Nikola");
        Workflow workflow = new Workflow();
        workflow.setFlowId("13");
        AdditionalRequest additionalRequest = new AdditionalRequest();
        additionalRequest.setCritical(true);
        additionalRequest.setName("Unknown workflow");
        additionalRequest.setRequest("Get new number of workflow");
        clusterAnswer.add(linkTo(methodOn(TestRestController.class).getWorkflow()).withSelfRel());
        return new ResponseEntity<>(clusterAnswer, HttpStatus.OK);
    }

    @RequestMapping(value = "/postJson", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public String postJson(@RequestBody Shutdown shutdown){
        System.out.println(shutdown.toString());
        return shutdown.toString();
    }


    @RequestMapping(value = "/clientTest")
    public String getClient(){
        String str = restTemplate.getForObject("http://jsonplaceholder.typicode.com/posts/1", String.class);
        return str;
    }



    @RequestMapping(value = "/postError",method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public ErrorAnswer post(@RequestBody ErrorAnswer errorAnswer){
        System.out.println(errorAnswer.toString());
        System.out.println("We received your error");
        return errorAnswer;
    }

//    @RequestMapping(value = "/postResult",method = RequestMethod.POST, headers = {"Content-type=application/json"})
//    public ClusterAnswer post(@RequestBody ClusterAnswerWrapper clusterAnswer){
//        System.out.println("We received your answer");
//        return clusterAnswer;
//    }


    @RequestMapping(value = "/postResult",method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public ClusterAnswer post(@RequestBody ClusterAnswer clusterAnswer){
        System.out.println("We received your answer");
        return clusterAnswer;
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ErrorAnswer post(){
        ErrorAnswer errorAnswer = new ErrorAnswer();
        errorAnswer.setError("My error");
        errorAnswer.setStep("123");
        errorAnswer.setTelegramId("99990");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ErrorAnswer> entity = new HttpEntity<>(errorAnswer, headers);

        ErrorAnswer object = restTemplate.postForObject("http://localhost:8080/postError", entity, ErrorAnswer.class);


        return errorAnswer;
    }

    @RequestMapping(value = "/admin/info", method = RequestMethod.GET)
    public String getAdminInfo(){
        return "This is admin info";
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public String getUserInfo(){
        return "This is user info";
    }


}
