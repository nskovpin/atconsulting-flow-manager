package ru.atconsulting.bigdata.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.atconsulting.bigdata.workflow.LoggerThread;
import ru.atconsulting.bigdata.workflow.xml.FlowHolder;
import ru.atconsulting.bigdata.workflow.xml.business.Flow;
import ru.atconsulting.bigdata.workflow.xml.technical.Instructions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@Configuration
@PropertySource(value = {
        "classpath:/application.properties",
        "classpath:/db/db.properties"
})
@EnableWebMvc
@ComponentScan(basePackages = "ru.atconsulting.bigdata")
@Import({
        SpringSecurityConfiguration.class,
        JpaConfiguration.class,
        JpaRepositoryConfiguration.class,
        SpringSecurityConfiguration.class,
        RestClientConfiguration.class
})
public class SpringConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Integer getSleepingTime(@Value("${logger.file.sleepTime}") Integer sleepTime) {
        return sleepTime;
    }

    @Bean
    public static LoggerThread getLoggerThread(@Value("${logger.file.path}") String loggerPath) {
        LoggerThread loggerThread = new LoggerThread(loggerPath);
        loggerThread.start();
        return loggerThread;
    }

    @Bean
    public Instructions getFlowInstructions(@Value("${flows.instruction}") String flowInstruction) throws JAXBException {
        File file = new File(flowInstruction);
        JAXBContext jaxbContext = JAXBContext.newInstance(Instructions.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Instructions) jaxbUnmarshaller.unmarshal(file);
    }

    @Bean
    @Autowired
    public FlowHolder getFlowHolder(@Value("${flows.path}") String flowsPath, Instructions instructions) throws JAXBException, IOException {
        File flowDirectory = new File(flowsPath);
        if (!flowDirectory.isDirectory()) {
            throw new RuntimeException("Set flow path directory!");
        }
        FlowHolder flowHolder = new FlowHolder();
        for (File file : flowDirectory.listFiles()) {
            if (file.isFile()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Flow.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Flow flow = (Flow) jaxbUnmarshaller.unmarshal(file);
                flow.setInstructions(instructions);
                flowHolder.addFlowToHolder(flow);
            }
        }
        if (flowHolder.getFlowIdAndFlowMap().size() == 0) {
            throw new RuntimeException("Cant find any flow");
        }
        return flowHolder;
    }

}
