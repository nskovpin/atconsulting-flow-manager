package ru.atconsulting.bigdata.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import ru.atconsulting.bigdata.client.telegram.impl.TelegramClientProperties;

import java.util.Properties;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@Configuration
@PropertySource("classpath:client/client.properties")
public class RestClientConfiguration {

    @Value("${client.telegram.name}")
    public String userName;

    @Value("${client.telegram.password}")
    public String userPassword;

    @Value("${client.telegram.url.additionalInfo}")
    public String additionalUrl;

    @Bean
    @Autowired
    public TelegramClientProperties getRestClientProperties(){
        return new TelegramClientProperties();
    }

    @Bean(name = "clientTelegramProperties")
    public Properties getClientTelegramProperties(@Value("${client.telegram.name}") String userName,
                                                  @Value("${client.telegram.password}") String userPassword,
                                                  @Value("${client.telegram.url.additionalInfo}") String additionalUrl,
                                                  @Value("${client.telegram.url.stepResult}") String stepResults
                                                  ){
        Properties properties = new Properties();
        properties.setProperty(TelegramClientPropertyNames.USER_NAME.getValue(), userName);
        properties.setProperty(TelegramClientPropertyNames.USER_PASSWORD.getValue(), userPassword);
        properties.setProperty(TelegramClientPropertyNames.STEP_RESULT_URL.getValue(), stepResults);
        properties.setProperty(TelegramClientPropertyNames.ADDITIONAL_INFO_URL.getValue(), additionalUrl);
        return  properties;
    }

    @Bean(name = "clientTsmsProperties")
    public Properties getClientTsmsProperties(){
        Properties properties = new Properties();
        return properties;
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public enum TelegramClientPropertyNames{

        USER_NAME("userName"),
        USER_PASSWORD("userPassword"),
        STEP_RESULT_URL("stepResult"),
        ADDITIONAL_INFO_URL("additionalInfo");

        private String value;
        TelegramClientPropertyNames(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }


}
