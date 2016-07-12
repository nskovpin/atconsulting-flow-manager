package ru.atconsulting.bigdata.client.telegram.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.atconsulting.bigdata.client.ClientProperties;

import java.util.Properties;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@Component("telegramProperties")
public class TelegramClientProperties implements ClientProperties {

    @Autowired
    @Qualifier(value = "clientTelegramProperties")
    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public TelegramClientProperties(Properties properties){
        this.properties = properties;
    }

    public TelegramClientProperties(){
    }

}
