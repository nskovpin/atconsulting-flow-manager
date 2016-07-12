package ru.atconsulting.bigdata.domain;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by NSkovpin on 09.06.2016.
 */
public class Hello extends ResourceSupport {

    private String information;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
