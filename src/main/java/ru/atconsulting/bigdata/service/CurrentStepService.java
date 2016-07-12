package ru.atconsulting.bigdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.repo.CurrentStepRepository;
import ru.atconsulting.bigdata.workflow.xml.business.Step;

import java.util.List;

/**
 * Created by NSkovpin on 22.06.2016.
 */
@Service
public class CurrentStepService {

    @Qualifier("currentStepRepository")
    @Autowired
    private CurrentStepRepository currentStepRepository;


    public CurrentStep findByTelegramIdAndCtnAndFlow(String telegramId, String ctn, String flow){
        return currentStepRepository.findByTelegramIdAndCtnAndFlow(telegramId, ctn, flow);
    }

    public List<CurrentStep> findByTelegramIdAndCtn(String telegramId, String ctn){
       return currentStepRepository.findByTelegramIdAndCtn(telegramId,ctn);
    }

    public void increaseCurrentStep(String telegramId, String ctn, String flow, Step step){
        CurrentStep  currentStep = currentStepRepository.findByTelegramIdAndCtnAndFlow(telegramId, ctn, flow);
        if(currentStep == null){
            currentStep = new CurrentStep();
            currentStep.setCtn(ctn);
            currentStep.setTelegramId(telegramId);
            currentStep.setFlow(flow);
        }
        currentStep.setCurrentStep(step.getId());
        currentStepRepository.save(currentStep);
    }

    public void removeCurrentStep(String telegramId){
        currentStepRepository.deleteByTelegramId(telegramId);
    }

    public void removeCurrentStep(String telegramId, String ctn, String flow){
        CurrentStep  currentStep = currentStepRepository.findByTelegramIdAndCtnAndFlow(telegramId, ctn, flow);
        if(currentStep != null){
            currentStepRepository.delete(currentStep);
        }
    }


}
