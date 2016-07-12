package ru.atconsulting.bigdata.workflow.xml.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Ok {

    @XmlAttribute
    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
