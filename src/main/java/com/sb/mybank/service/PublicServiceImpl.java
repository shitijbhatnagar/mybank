package com.sb.mybank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Slf4j
@Service
public class PublicServiceImpl implements PublicService
{
    String host;

    public boolean IsPublicAPIAvailable(String apiEndpoint)
    {
        //Setup the API call
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        log.debug("Public API being checked @ " + host + apiEndpoint);

        //Execute the API call and return its availability status
        return new RestTemplate().exchange(host + apiEndpoint, HttpMethod.GET, entity, String.class).getStatusCode().is2xxSuccessful();
    }

    public void setHost(String host) {
        this.host = host;
    }

}
