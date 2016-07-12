package ru.atconsulting.bigdata.domain.answer;

/**
 * Created by NSkovpin on 10.06.2016.
 */
public class AdditionalRequest {

    private String name;

    private Boolean critical;

    private String request;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
