package ru.atconsulting.bigdata.workflow.actions;

import ru.atconsulting.bigdata.workflow.xml.business.Step;

/**
 * Created by NSkovpin on 20.06.2016.
 */
public interface Action {

    Object run() throws Exception;

    Step getStep();

}
