package ru.atconsulting.bigdata.client.telegram.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.atconsulting.bigdata.client.ClientProperties;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;

/**
 * Created by NSkovpin on 08.06.2016.
 */
@Component("telegramClient")
public class TelegramRestClient implements RestClient {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("telegramProperties")
    private ClientProperties clientProperties;


    @Override
    public ClientProperties getClientProperties() {
        return clientProperties;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public <T> T getInformationWithUrl(Class<T> className, String propertyUrlName) throws RestClientException{
        T obj = restTemplate.getForObject(clientProperties.getProperties().getProperty(propertyUrlName).toString(),
                className);
        return obj;
    }

    @Override
    public <T> T postInformationWithUrl(String url, Object request, Class<T> response) throws RestClientException{
        return restTemplate.postForObject(url, request, response);
    }

    @Override
    public <T> ClusterAnswer<T> exchange(String url, Class<T> tClass, HttpMethod httpMethod, HttpEntity<?> entity) {
//        ClusterAnswer<T> response = restTemplate.exchange(url,
//                httpMethod,
//                entity,
//                ParameterizedTypeMap.getParameterizedType(tClass)).getBody();
//        return  response;
        return null;
    }


}
