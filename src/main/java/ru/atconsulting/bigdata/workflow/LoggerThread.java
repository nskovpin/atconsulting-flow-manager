package ru.atconsulting.bigdata.workflow;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by NSkovpin on 17.06.2016.
 */
public class LoggerThread extends Thread {

    private final String loggerPath;

    private Integer sleepTime = 1000;

    @Autowired
    public LoggerThread(String loggerPath) {
        this.loggerPath = loggerPath;
    }

    @Autowired
    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    private static final Logger logger = Logger.getLogger(LoggerThread.class);

    private CopyOnWriteArrayList<String> logBuffer = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        while (true) {
            System.out.println(">Logger thread");
            long milliSeconds = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date resultDate = new Date(milliSeconds);
            writeLog(sdf.format(resultDate), logBuffer);
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addLog(String log, Class<?> className) {
        long milliSeconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultDate = new Date(milliSeconds);
        String logFormatted = "[Class:" + className.getCanonicalName() + "; Time:" +
                sdf.format(resultDate) + " : Message:" + log + "]";
        logBuffer.add(logFormatted);
    }

    public void writeLog(String name, CopyOnWriteArrayList<String> logBuffer) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : logBuffer) {
            stringBuilder.append(str).append("\n");
        }
        logBuffer.clear();
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(loggerPath + File.separator + name + "_log.txt"),
                    true));
            try {
                out.append(stringBuilder);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
