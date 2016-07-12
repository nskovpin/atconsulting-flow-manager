package ru.atconsulting.bigdata.domain.steps;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * Created by NSkovpin on 23.06.2016.
 */
@Embeddable
public class TelegramCtnFlowPrimaryKey implements Serializable {

    @Column(name = "telegramId", nullable = false)
    private String telegramId;

    @Column(name = "ctn", nullable = false)
    private String ctn;

    @Column(name = "flow", nullable = false)
    private String flow;


    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }
}
