package ru.atconsulting.bigdata.domain.flow;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by NSkovpin on 08.06.2016.
 */
public class Workflow extends ResourceSupport {

    private String flowId;

    private String flowName;

    private String flowAdditionalInfo;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowAdditionalInfo() {
        return flowAdditionalInfo;
    }

    public void setFlowAdditionalInfo(String flowAdditionalInfo) {
        this.flowAdditionalInfo = flowAdditionalInfo;
    }

    @Override
    public String toString() {
        return "Workflow:[flowId="+flowId+
                ",flowName="+flowName+
                ",flowAdditionalInfo="+flowAdditionalInfo+
                "]"+super.toString();
    }
}
