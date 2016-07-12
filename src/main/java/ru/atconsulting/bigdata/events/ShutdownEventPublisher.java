package ru.atconsulting.bigdata.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

/**
 * Created by NSkovpin on 10.06.2016.
 */
@Component
public class ShutdownEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishShutdown(Object source, String telegramId){
        applicationEventPublisher.publishEvent(new ShutdownEvent(source, telegramId));
    }
}
