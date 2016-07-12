import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.atconsulting.bigdata.configuration.SpringConfiguration;
import ru.atconsulting.bigdata.domain.answer.StepAnswer;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.domain.steps.StepResults;
import ru.atconsulting.bigdata.repo.CurrentStepRepository;
import ru.atconsulting.bigdata.repo.StepResultsRepository;
import ru.atconsulting.bigdata.service.StepResultsService;

import java.util.List;

/**
 * Created by NSkovpin on 21.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
@WebAppConfiguration
public class JpaTest {

    @Qualifier("currentStepRepository")
    @Autowired
    private CurrentStepRepository currentStepRepository;

    @Qualifier("stepResultsRepository")
    @Autowired
    private StepResultsRepository stepResultsRepository;

    @Autowired
    private StepResultsService stepResultsService;

    @Test
    public void readResultsTest(){
        List<StepResults> stepAnswerList = stepResultsRepository.findAll();
        System.out.println("stepAnswer");
    }

    @Test
    public void readTest(){
        List<CurrentStep> currentStepList = currentStepRepository.findAll();
        Assert.assertNotNull(currentStepList);
    }

    @Test
    public void readAndUpdate(){

        CurrentStep currentStepSingle = new CurrentStep();
        currentStepSingle.setCtn("123123");
        currentStepSingle.setTelegramId("112");
        currentStepSingle.setFlow("12");
        List<CurrentStep> s = currentStepRepository.findByTelegramId("112");
        if(s == null && s.isEmpty()){
            currentStepRepository.save(currentStepSingle);
        }

        List<CurrentStep> currentStep = currentStepRepository.findByTelegramId("112");
        String step = currentStep.get(0).getCurrentStep();
        currentStep.get(0).setCurrentStep("" + (Integer.parseInt(step) + 1));
        currentStepRepository.save(currentStep);
        currentStep = currentStepRepository.findByTelegramId("112");
        Assert.assertTrue(Integer.parseInt(step) + 1 == Integer.parseInt(currentStep.get(0).getCurrentStep()));
    }

    @Test
    public void deleteTest(){
        String telegramId = "666";
        String ctn = "988841";
        String flow = "181";
        CurrentStep cur = new CurrentStep();
        cur.setCurrentStep("2");
        cur.setTelegramId(telegramId);
        cur.setCtn(ctn);
        cur.setFlow(flow);
        currentStepRepository.save(cur);

        StepResults stepResults = new StepResults();
        stepResults.setStep("12");
        stepResults.setCurrentStep(currentStepRepository.findByTelegramIdAndCtnAndFlow(telegramId,ctn,flow));
        stepResultsRepository.save(stepResults);


        Assert.assertNotNull(currentStepRepository.findByTelegramIdAndCtnAndFlow(telegramId,ctn,flow));

        Assert.assertNotNull(currentStepRepository.findByTelegramId(telegramId));

        currentStepRepository.deleteByTelegramId(telegramId);
        Assert.assertTrue(currentStepRepository.findByTelegramId(telegramId).isEmpty());

    }

    @Test
    public void customTypeTest(){

        StepAnswer stepAnswer = new StepAnswer();
        stepAnswer.setAction("Check");
        stepAnswer.setStep("0");
        stepAnswer.setTelegramId("111222");
        stepAnswer.setInfo("result is good");

        CurrentStep currentStep = new CurrentStep();
        currentStep.setCtn("951");
        currentStep.setTelegramId("112");
        currentStep.setFlow("1");
        if(currentStepRepository.findByTelegramIdAndCtnAndFlow(currentStep.getTelegramId(), currentStep.getCtn(), currentStep.getFlow()) == null){
            currentStepRepository.save(currentStep);
        }

        CurrentStep currentStep1 = currentStepRepository.findByTelegramIdAndCtnAndFlow("112", "951","1");


        StepResults stepResults = new StepResults();
        stepResults.setResult(stepAnswer);
        stepResults.setCurrentStep(currentStep1);
        stepResults.setStep("5");

        stepResultsRepository.save(stepResults);
        List<StepResults> stepResultsList = stepResultsRepository.findAll();
        Assert.assertNotNull(stepResultsList);
    }


    @Test
    public void updateTest(){
        currentStepRepository.deleteByTelegramId("234234");
    }

    @Test
    public void deleteByCurrentStep(){
        List<CurrentStep> currentSteps = currentStepRepository.findByTelegramId("112");
        stepResultsService.removeResultsByCurrentSteps(currentSteps);
        int size = stepResultsRepository.findAll().size();
        List<StepResults> resultsList = stepResultsService.findByCurrentStep(currentSteps.get(0));
        Assert.assertNotNull(stepResultsService.findByCurrentStep(currentSteps.get(0)));
    }


    @Test
    public void deleteStep(){
        currentStepRepository.deleteByTelegramId("12340001234");

        List<CurrentStep> currentSteps = currentStepRepository.findAll();
        System.out.println("123");
    }


}
