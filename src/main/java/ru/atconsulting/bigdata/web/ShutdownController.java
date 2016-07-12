package ru.atconsulting.bigdata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.atconsulting.bigdata.domain.Shutdown;
import ru.atconsulting.bigdata.events.ShutdownEvent;
import ru.atconsulting.bigdata.events.ShutdownEventPublisher;
import ru.atconsulting.bigdata.workflow.LoggerThread;
import ru.atconsulting.bigdata.workflow.ThreadMap;

/**
 * Created by NSkovpin on 10.06.2016.
 */
@RestController
@RequestMapping("/rest/workflow")
public class ShutdownController {

    @Autowired
    private ShutdownEventPublisher shutdownEventPublisher;

    @Autowired
    private ThreadMap threadMap;

    @Autowired
    private LoggerThread loggerThread;

    @RequestMapping(value = "/shutdown", method = RequestMethod.GET)
    public ResponseEntity<Shutdown> shutdown(@RequestParam(value = "telegramId", required = true) String telegramId) {
        shutdownEventPublisher.publishShutdown(telegramId, telegramId);
        boolean removed = threadMap.stopAndRemoveThread(telegramId);
        Shutdown shutdown = new Shutdown();
        return getResponseEntity(removed, telegramId);
    }

    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    public ResponseEntity<Shutdown> shutdownSubscriber(@RequestBody Shutdown shutdown) {
        shutdownEventPublisher.publishShutdown(shutdown.getTelegramId(), shutdown.getTelegramId());
        boolean removed = threadMap.stopAndRemoveThread(shutdown.getTelegramId());
        return getResponseEntity(removed, shutdown.getTelegramId());
    }

    private ResponseEntity<Shutdown> getResponseEntity(boolean removed, String telegramId){
        Shutdown shutdown = new Shutdown();
        if (removed) {
            loggerThread.addLog("Threads with telegramId:" + telegramId + " has been killed", ShutdownController.class);
            shutdown.setTelegramId(telegramId);
            shutdown.setStatus("SHUTDOWN");
            shutdown.setInfo("Shutdown completed");
            return new ResponseEntity<>(shutdown, HttpStatus.OK);
        } else {
            loggerThread.addLog("Didn't find thread with telegramId:"+ telegramId, ShutdownController.class);
            shutdown.setTelegramId(telegramId);
            shutdown.setStatus("NOT FOUND");
            shutdown.setInfo("Didn't find thread with telegramId:"+ telegramId);
            return new ResponseEntity<Shutdown>(shutdown, HttpStatus.OK);
        }
    }

}
