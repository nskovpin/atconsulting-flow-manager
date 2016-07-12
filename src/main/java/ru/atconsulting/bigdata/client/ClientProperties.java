package ru.atconsulting.bigdata.client;

import java.util.Properties;

/**
 * Created by NSkovpin on 15.06.2016.
 */
public interface ClientProperties {

    Properties getProperties();

    String getProperty(String propertyName);

}
