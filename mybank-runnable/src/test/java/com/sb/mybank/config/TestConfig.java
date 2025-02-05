package com.sb.mybank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(RootConfig.class)
@Configuration
public class TestConfig
{
    //Automatic import of ObjectMapper bean from RootConfig
}