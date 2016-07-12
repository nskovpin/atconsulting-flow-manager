package ru.atconsulting.bigdata.workflow.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.services.ServiceClient;
import ru.atconsulting.bigdata.services.tsms.impl.TsmsClient;
import ru.atconsulting.bigdata.services.tsms.impl.TsmsClientProperties;
import ru.atconsulting.bigdata.services.tsms.impl.TsmsParams;
import ru.atconsulting.bigdata.services.tsms.impl.TsmsServices;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

import java.util.Properties;

/**
 * Created by NSkovpin on 30.06.2016.
 */
@Component("TsmAction")
@Scope("prototype")
public class TsmsAction implements Action {

    private Step step;

    private String ctn;

    public TsmsAction(Step step, String ctn) {
        this.step = step;
        if (ctn != null && ctn.length() == 10) {
            ctn = "7" + ctn;
        }
        this.ctn = ctn;
    }

    @Override
    public Object run() throws Exception {

        ServiceClient tsmsClient = new TsmsClient();

        TsmsClientProperties properties = new TsmsClientProperties();
        Properties prop = new Properties();
        prop.setProperty(TsmsParams.username.name(), step.getExecutorMappingInstruction().getParams().getName());
        prop.setProperty(TsmsParams.password.name(), step.getExecutorMappingInstruction().getParams().getPassword());
        prop.setProperty(TsmsParams.service_url.name(), step.getExecutorMappingInstruction().getParams().getUrl());
        prop.setProperty(TsmsParams.ctn.name(), ctn);
        properties.setProperties(prop);

        Object object =  tsmsClient.getServiceResult(TsmsServices.LocationService, properties);
        return object;
    }

    @Override
    public Step getStep() {
        return step;
    }

}
