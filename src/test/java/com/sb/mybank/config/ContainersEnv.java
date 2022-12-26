package com.sb.mybank.config;

import com.sb.mybank.containers.PostgreSQLTestContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//Config for the PostgreSQLContainer
@Testcontainers
public class ContainersEnv {

   @Container
    public static PostgreSQLContainer postgreSQLContainer = PostgreSQLTestContainer.getInstance();
}