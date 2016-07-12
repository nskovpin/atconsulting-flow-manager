package ru.atconsulting.bigdata.domain.visit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by NSkovpin on 12.07.2016.
 */
@Embeddable
public class TelegramDatePrimaryKey implements Serializable {

    @Column(name = "telegramId", nullable = false)
    private String telegramId;


}
