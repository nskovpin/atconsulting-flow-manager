package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswerWrapper;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@Component("ReturnResultAction")
@Scope("prototype")
public class ReturnResultAction<T extends ClusterAnswer> implements Action {

    @Autowired
    @Qualifier("telegramClient")
    private RestClient telegramRestClient;

    private T sendingObject;

    private Step step;

    public ReturnResultAction(Step step, T sendingObject){
        this.step = step;
        this.sendingObject = sendingObject;
    }

    @Override
    public Object run() {
        String url = step.getExecutorMappingInstruction().getParams().getUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> entity = new HttpEntity<>(sendingObject, headers);
        ClusterAnswerWrapper response = telegramRestClient.postInformationWithUrl(url, entity, ClusterAnswerWrapper.class);

        if(response == null){
            throw new RuntimeException("Response is null. Used url:" + url);
        }

        return response.getStepAnswer();
    }

    @Override
    public Step getStep() {
        return step;
    }
}
