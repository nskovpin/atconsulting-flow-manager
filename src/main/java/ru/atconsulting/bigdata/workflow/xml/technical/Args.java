package ru.atconsulting.bigdata.workflow.xml.technical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Args {

    @XmlElement
    private List<String> value;

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
