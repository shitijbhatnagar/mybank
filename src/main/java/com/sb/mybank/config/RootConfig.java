package com.sb.mybank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Slf4j
@Configuration
public class RootConfig {

    static
    {
        log.debug("RootConfig: Logging available security providers:");
        for (java.security.Provider provider : java.security.Security.getProviders())
        {
            log.debug(provider.getInfo());
            for (Map.Entry<Object, Object> entry : provider.entrySet())
            {
                log.debug("\t" + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}