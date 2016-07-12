package ru.atconsulting.bigdata.workflow.xml.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by NSkovpin on 08.07.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Execute {

    @XmlAttribute
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
