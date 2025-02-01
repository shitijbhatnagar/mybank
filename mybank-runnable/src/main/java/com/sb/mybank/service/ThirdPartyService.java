package com.sb.mybank.service;

import org.springframework.stereotype.Component;

@Component
public interface ThirdPartyService {
    public boolean IsServiceAvailable(String host);
}
