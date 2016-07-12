package ru.atconsulting.bigdata.workflow;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NSkovpin on 14.06.2016.
 */
@Scope("singleton")
@Component
public class ThreadMap {

    @Autowired
    private LoggerThread loggerThread;

    private Map<String, List<Thread>> telegramIdAndThread;

    public void addElement(String key, Thread value) {
        if (telegramIdAndThread == null) {
            telegramIdAndThread = new HashMap<>();
        }
        List<Thread> threadList = telegramIdAndThread.get(key);
        if (threadList == null) {
            threadList = new ArrayList<>();
        }
        threadList.add(value);
        telegramIdAndThread.put(key, threadList);
    }

    public boolean stopAndRemoveThread(String telegramId) {
        if (telegramIdAndThread != null) {
            if (telegramIdAndThread.containsKey(telegramId)) {
                List<Thread> threadList = telegramIdAndThread.get(telegramId);
                threadList.forEach(thread -> thread.interrupt());
                telegramIdAndThread.remove(telegramId);
                loggerThread.addLog("Removing key " + telegramId + " from thread map", ThreadMap.class);
                return true;
            }
        }
        return false;
    }

}
