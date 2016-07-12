package ru.atconsulting.bigdata.domain.steps;

import javax.persistence.*;
import java.util.*;

/**
 * Created by NSkovpin on 21.06.2016.
 */
@Entity
@Table(name = "step"
        , uniqueConstraints = {
        @UniqueConstraint(columnNames = "telegramId"),
        @UniqueConstraint(columnNames = "ctn"),
        @UniqueConstraint(columnNames = "flow")
}
)
public class CurrentStep {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "telegramId", nullable = false)
    private String telegramId;

    @Column(name = "currentStep")
    private String currentStep;

    @Column(name = "ctn", nullable = false)
    private String ctn;

    @Column(name = "flow", nullable = false)
    private String flow;

    private Set<StepResults> stepResultsList = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    @OneToMany(mappedBy = "curStep", cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    public Set<StepResults> getStepResultsList(){
        return this.stepResultsList;
    }

    public void setStepResultsList(Set<StepResults> stepResultsList){
        this.stepResultsList = stepResultsList;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }
}
