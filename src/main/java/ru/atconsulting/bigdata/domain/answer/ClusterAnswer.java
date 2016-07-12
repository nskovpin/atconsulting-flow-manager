package ru.atconsulting.bigdata.domain.answer;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;
import ru.atconsulting.bigdata.domain.flow.Workflow;
import ru.atconsulting.bigdata.domain.user.UserInfo;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NSkovpin on 08.06.2016.
 */
public class ClusterAnswer<T> extends ResourceSupport {

    private UserInfo userInfo;

    private Workflow workflow;

    private StepAnswer<T> stepAnswer;

    private ErrorAnswer errorAnswer;

    private String status;

//    private Class<T> genericClass;
//
//    @JsonIgnore
//    public Class<T> getGenericClass(){
//        return genericClass;
//    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public StepAnswer<T> getStepAnswer() {
        return stepAnswer;
    }

    public void setStepAnswer(StepAnswer<T> stepAnswer) {
//        this.genericClass = Class<T>;
        this.stepAnswer = stepAnswer;
    }

    public ErrorAnswer getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(ErrorAnswer errorAnswer) {
        this.errorAnswer = errorAnswer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
