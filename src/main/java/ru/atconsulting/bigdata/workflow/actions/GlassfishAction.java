package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.services.ServiceClient;
import ru.atconsulting.bigdata.services.glassfish.impl.GlassfishClientProperties;
import ru.atconsulting.bigdata.services.glassfish.impl.GlassfishParams;
import ru.atconsulting.bigdata.services.glassfish.impl.GlassfishServices;
import ru.atconsulting.bigdata.services.glassfish.impl.GlassfishSisSoapClient;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@Component("GlassfishAction")
@Scope("prototype")
public class GlassfishAction implements Action{

    private Step step;

    private String ctn;

    private List<String> requiredInfoList;

    public GlassfishAction(Step step, String ctn, List<String> requiredInfoList){
        this.step = step;
        this.ctn = ctn;
        this.requiredInfoList = requiredInfoList;
    }

    @Override
    public Object run() throws Exception{

        GlassfishClientProperties glassfishClientProperties = new GlassfishClientProperties();
        Properties properties = new Properties();
        properties.setProperty(GlassfishParams.name.name(), step.getExecutorMappingInstruction().getParams().getName());
        properties.setProperty(GlassfishParams.password.name(), step.getExecutorMappingInstruction().getParams().getPassword());
        properties.setProperty(GlassfishParams.url.name(), step.getExecutorMappingInstruction().getParams().getUrl());
        properties.setProperty(GlassfishParams.ctn.name(), ctn);
        glassfishClientProperties.setProperties(properties);
        glassfishClientProperties.setAdditionalParams(requiredInfoList);

        ServiceClient glassfishSoapClient = new GlassfishSisSoapClient();
        Object response = glassfishSoapClient.getServiceResult(GlassfishServices.valueOf(step.getExecutorMappingInstruction().getParams().getService()),
                glassfishClientProperties);

        return response;
    }

    @Override
    public Step getStep() {
        return step;
    }

}
