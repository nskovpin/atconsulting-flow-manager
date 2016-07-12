package ru.atconsulting.bigdata.workflow.xml.technical;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by NSkovpin on 20.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Params {

    @XmlElement
    private String name;

    @XmlElement
    private String password;

    @XmlElement
    private String service;

    @XmlElement
    private String url;

    @XmlElement
    private Args args;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }
}
