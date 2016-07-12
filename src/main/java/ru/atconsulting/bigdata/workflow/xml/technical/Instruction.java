package ru.atconsulting.bigdata.workflow.xml.technical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Instruction {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String link;

    @XmlElement
    private Params params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
