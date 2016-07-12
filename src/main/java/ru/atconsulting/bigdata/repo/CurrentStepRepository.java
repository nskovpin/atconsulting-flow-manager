package ru.atconsulting.bigdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;

import java.util.List;

/**
 * Created by NSkovpin on 21.06.2016.
 */
@Repository
@Transactional
public interface CurrentStepRepository extends JpaRepository<CurrentStep, Integer> {

    List<CurrentStep> findByTelegramId(String telegramId);

    void deleteByTelegramId(String telegramId);

    List<CurrentStep> findByTelegramIdAndCtn(String telegramId, String ctn);

    CurrentStep findByTelegramIdAndCtnAndFlow(String telegramId, String ctn, String flow);

}
