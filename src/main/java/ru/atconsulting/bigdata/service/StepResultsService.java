package ru.atconsulting.bigdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.atconsulting.bigdata.domain.answer.Answer;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.domain.steps.StepResults;
import ru.atconsulting.bigdata.repo.StepResultsRepository;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

import java.util.List;

/**
 * Created by NSkovpin on 22.06.2016.
 */
@Service
public class StepResultsService {

    @Autowired
    private CurrentStepService currentStepService;

    @Qualifier("stepResultsRepository")
    @Autowired
    private StepResultsRepository stepResultsRepository;

    public void addStepResult(Answer stepAnswer, Step step, CurrentStep currentStep) {
        StepResults stepResults = new StepResults();
        stepResults.setResult(stepAnswer);
        stepResults.setStep(step.getId());
        stepResults.setCurrentStep(currentStep);
        stepResultsRepository.save(stepResults);
    }

    public List<StepResults> findByCurrentStep(CurrentStep currentStep) {
        Integer id = currentStep.getId();
        return stepResultsRepository.findByCurStep(currentStep);
    }

    public void removeResultsByCurrentStep(CurrentStep currentStep) {
        Integer id = currentStep.getId();
        List<StepResults> stepResultsList = stepResultsRepository.findByCurStep(currentStep);
        if (stepResultsList != null) {
            for (StepResults stepResults : stepResultsList) {
                stepResultsRepository.delete(stepResults);
            }
        }
    }

    public void removeResultsByCurrentSteps(List<CurrentStep> currentStepList) {
        for (CurrentStep currentStep : currentStepList) {
            Integer id = currentStep.getId();
            List<StepResults> stepResultsList = stepResultsRepository.findByCurStep(currentStep);
            if (stepResultsList != null && !stepResultsList.isEmpty()) {
                for (StepResults stepResults : stepResultsList) {
                    stepResultsRepository.delete(stepResults);
                }
            }
        }
    }


}
