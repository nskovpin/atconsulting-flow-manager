package ru.atconsulting.bigdata.workflow.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.workflow.xml.business.Flow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NSkovpin on 08.07.2016.
 */
public class FlowHolder {

    private Map<String, Flow> flowIdAndFlow;

    public void addFlowToHolder(Flow flow) {
        if (flowIdAndFlow == null) {
            flowIdAndFlow = new HashMap<>();
        }
        flowIdAndFlow.put(flow.getId(), flow);
    }

    public Flow getFlowById(String flowId) {
        return flowIdAndFlow.get(flowId);
    }

    public void setFlowIdAndFlow(Map<String, Flow> flowIdAndFlow) {
        this.flowIdAndFlow = flowIdAndFlow;
    }

    public Map<String, Flow> getFlowIdAndFlowMap() {
        return flowIdAndFlow;
    }


}
