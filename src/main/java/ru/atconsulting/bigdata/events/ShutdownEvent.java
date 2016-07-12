package ru.atconsulting.bigdata.events;

import org.springframework.context.ApplicationEvent;

/**
 * Created by NSkovpin on 10.06.2016.
 */
public class ShutdownEvent extends ApplicationEvent{

    private String telegramId;

    public ShutdownEvent(Object source, String telegramId){
        super(source);
        this.telegramId = telegramId;
    }

    public String getTelegramId() {
        return telegramId;
    }

}
