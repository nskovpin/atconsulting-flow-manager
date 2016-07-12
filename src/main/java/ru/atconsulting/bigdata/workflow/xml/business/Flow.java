package ru.atconsulting.bigdata.workflow.xml.business;

import ru.atconsulting.bigdata.workflow.xml.technical.Instruction;
import ru.atconsulting.bigdata.workflow.xml.technical.Instructions;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flow {

    @XmlAttribute(required = true)
    private String id;

    @XmlElement(required = true)
    private Start start;

    @XmlElement(required = true)
    private List<Step> step;

    private List<Instruction> instructionMappingList;

    private Instructions instructions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Step> getStep() {
        return step;
    }

    public void setStep(List<Step> step) {
        this.step = step;
    }

    public void setInstructions(Instructions instructions) {
        this.instructions = instructions;
        if (instructionMappingList == null) {
            instructionMappingList = new ArrayList<>();
        }
        Map<String, Instruction> nameAndInstruction = new HashMap<>();
        instructions.getInstruction().forEach((Instruction inst) -> nameAndInstruction.put(inst.getLink(), inst));

        List<Step> orderedSteps = getOrderedSteps();

        for (Step s : orderedSteps) {
            Instruction instruction = nameAndInstruction.get(s.getExecute().getName());
            if (instruction == null) {
                throw new RuntimeException("There is no instruction for step");
            }
            s.setExecutorMappingInstruction(instruction);
            instructionMappingList.add(instruction);
        }
    }

    public List<Instruction> getInstructionMappingList() {
        return instructionMappingList;
    }

    public List<Step> getOrderedSteps() {
        List<Step> orderedStepList = new ArrayList<>();
        String startingStep = start.getTo();

        for (Step singleStep : step) {
            Step foundStep = getStepByID(step, startingStep);
            orderedStepList.add(foundStep);
            startingStep = foundStep.getOk().getTo();
            if (singleStep.getOk().getTo().equals("end")) {
                break;
            }
        }
        return orderedStepList;
    }


    public Step getStepByID(List<Step> stepList, String id) {
        for (Step step : stepList) {
            if (step.getId().equals(id)) {
                return step;
            }
        }
        throw new RuntimeException("Can't find step by id:" + id + ". Check flow.xml!");
    }

    public Step getReloadStepByID(List<Step> stepList, String id) {
        Step savedStep = getStepByID(stepList, id);
        return getStepByID(stepList, savedStep.getOk().getTo());
    }


}
