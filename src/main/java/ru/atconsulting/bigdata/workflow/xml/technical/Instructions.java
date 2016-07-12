package ru.atconsulting.bigdata.workflow.xml.technical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by NSkovpin on 08.07.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Instructions {

    @XmlElement(required = true)
    List<Instruction> instruction;

    public List<Instruction> getInstruction() {
        return instruction;
    }

    public void setInstruction(List<Instruction> instruction) {
        this.instruction = instruction;
    }
}
