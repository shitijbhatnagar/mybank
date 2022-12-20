package com.sb.mybank.service;

import org.springframework.stereotype.Component;

@Component
public interface PublicService {
    public boolean IsPublicAPIAvailable(String host);
}
