package com.sb.mybank.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Configuration;

import java.security.Security;
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
}