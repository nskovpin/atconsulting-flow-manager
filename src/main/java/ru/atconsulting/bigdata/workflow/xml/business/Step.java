package ru.atconsulting.bigdata.workflow.xml.business;

import ru.atconsulting.bigdata.workflow.xml.technical.Instruction;
import ru.atconsulting.bigdata.workflow.xml.technical.Params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Step {

    @XmlAttribute(required = true)
    private String id;

    @XmlElement(required = true)
    private Execute execute;

    @XmlElement
    private Ok ok;

    @XmlElement
    private Error error;

    private Instruction executorMappingInstruction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Execute getExecute() {
        return execute;
    }

    public void setExecute(Execute execute) {
        this.execute = execute;
    }

    public Ok getOk() {
        return ok;
    }

    public void setOk(Ok ok) {
        this.ok = ok;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Instruction getExecutorMappingInstruction() {
        return executorMappingInstruction;
    }

    public void setExecutorMappingInstruction(Instruction executorMappingInstruction) {
        this.executorMappingInstruction = executorMappingInstruction;
    }
}
