package ru.atconsulting.bigdata.events;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by NSkovpin on 14.06.2016.
 */
@Component
public class ShutdownHandler implements ApplicationListener<ShutdownEvent> {

    private static final Logger logger = Logger.getLogger(ShutdownHandler.class);

    private String telegramStopId;

    @Override
    public void onApplicationEvent(ShutdownEvent shutdownEvent) {
        logger.info(">Handle shutdown event");
        telegramStopId = shutdownEvent.getTelegramId();
    }

}
