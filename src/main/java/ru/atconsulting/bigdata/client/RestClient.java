package ru.atconsulting.bigdata.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.atconsulting.bigdata.domain.answer.ClusterAnswer;

/**
 * Created by NSkovpin on 15.06.2016.
 */
public interface RestClient {


    ClientProperties getClientProperties();

    RestTemplate getRestTemplate();

    <T> T getInformationWithUrl(Class<T> className, String propertyUrlName) throws RestClientException ;

    <T> T postInformationWithUrl(String url, Object request, Class<T> response) throws RestClientException ;

    <T> ClusterAnswer<T> exchange(String url, Class<T> tClass, HttpMethod httpMethod, HttpEntity<?> entity);


}
