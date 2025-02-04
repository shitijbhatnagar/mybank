package com.sb.mybank.remote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sb.mybank.config.RootConfig;
import com.sb.mybank.remote.service.RemoteTransactionAppService;
import com.sb.mybank.remote.service.RemoteTransactionAppServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RemoteTransactionConfig
{
    @Value("${remote.transaction.app.url}")
    private String transactionRemoteUrl;

    @Bean(name="remoteTransactionAppService")
    public RemoteTransactionAppService remoteTransactionAppService()
    {
        RemoteTransactionAppService service = new RemoteTransactionAppServiceImpl(getTransactionRemoteUrl(), remoteRestTemplate());
        return service;
    }

    @Bean(name="remoteRestTemplate")
    public RestTemplate remoteRestTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public String getTransactionRemoteUrl()
    {
        return transactionRemoteUrl;
    }

    @Bean(name="testObjectMapper")
    public ObjectMapper testObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
