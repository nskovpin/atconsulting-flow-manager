package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.configuration.RestClientConfiguration;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 24.06.2016.
 */
@Component("additionalRequestAction")
public class AdditionalRequestAction implements Action {

    @Autowired
    @Qualifier(value ="telegramClient")
    private RestClient restClient;

    private Step step;

    private String propertyName;

    public AdditionalRequestAction(Step step, String propertyName){
        this.step = step;
        this.propertyName = propertyName;
    }

    public AdditionalRequestAction(){

    }

    @Override
    public Object run() throws Exception {
        Object object = restClient.getInformationWithUrl(Object.class,
                RestClientConfiguration.TelegramClientPropertyNames.STEP_RESULT_URL.getValue());
        return object;
    }

    @Override
    public Step getStep() {
        return step;
    }

}
