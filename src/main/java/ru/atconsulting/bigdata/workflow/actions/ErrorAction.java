package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswerWrapper;
import ru.atconsulting.bigdata.service.CurrentStepService;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@Component("ErrorAction")
@Scope("prototype")
public class ErrorAction<T> implements Action {

    @Qualifier("telegramClient")
    @Autowired
    private RestClient telegramRestClient;

    @Autowired
    private CurrentStepService currentStepService;

    private Step step;

    private T sendingObject;

    private String telegramId;

    private String ctn;

    private String flow;

    public ErrorAction(Step step, String telegramId, String ctn, String flow, T sendingObject){
        this.step = step;
        this.sendingObject = sendingObject;
        this.telegramId = telegramId;
        this.ctn = ctn;
        this.flow = flow;
    }

    @Override
    public Object run() throws Exception {

        String url = step.getExecutorMappingInstruction().getParams().getUrl();
        if(url.isEmpty()){
            throw new RuntimeException("Empty params! Check action in xml, check action's params");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> entity = new HttpEntity<>(sendingObject, headers);
        ClusterAnswerWrapper response = telegramRestClient.postInformationWithUrl(url, entity, ClusterAnswerWrapper.class);
        if(response == null){
            throw new RuntimeException("Response is null. Used url:"+url);
        }

        return response.getStepAnswer();
    }

    @Override
    public Step getStep() {
        return null;
    }
}
