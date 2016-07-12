package ru.atconsulting.bigdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.atconsulting.bigdata.domain.steps.CurrentStep;
import ru.atconsulting.bigdata.domain.steps.StepResults;

import java.util.List;

/**
 * Created by NSkovpin on 22.06.2016.
 */
@Repository
public interface StepResultsRepository extends JpaRepository<StepResults, Integer>{

    @Query("FROM StepResults L WHERE L.curStep = :curStep")
    List<StepResults> findByCurStep(@Param(value = "curStep") CurrentStep curStep);

}
