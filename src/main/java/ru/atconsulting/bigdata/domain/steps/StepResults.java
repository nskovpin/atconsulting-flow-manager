package ru.atconsulting.bigdata.domain.steps;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.atconsulting.bigdata.domain.answer.Answer;
import ru.atconsulting.bigdata.domain.answer.StepAnswer;
import ru.atconsulting.bigdata.domain.answer.StepAnswerType;

import javax.persistence.*;

/**
 * Created by NSkovpin on 22.06.2016.
 */
@Entity
@Table(name = "results")
@TypeDefs({
        @TypeDef(name = StepAnswerType.NAME, typeClass = StepAnswerType.class)
})
public class StepResults {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "step")
    private String step;

    @Column(name = "result")
    @Type(type = StepAnswerType.NAME)
    private Answer result;

    @ManyToOne()
    @JoinColumn(name = "curStep")
    private CurrentStep curStep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Answer getResult() {
        return result;
    }

    public void setResult(Answer result) {
        this.result = result;
    }

    public CurrentStep getCurrentStep() {
        return curStep;
    }

    public void setCurrentStep(CurrentStep currentStep) {
        this.curStep = currentStep;
    }
}
