package com.sb.mybank.containers;

//import org.testcontainers.containers.PostgreSQLContainer;

//Create the PostgreSQL Test container
public class PostgreSQLTestContainer //extends PostgreSQLContainer<PostgreSQLTestContainer>
{
    public static final String POSTGRESQL_VERSION = "postgres:11.1";
    public static final String DB_NAME = "test";
//    public static PostgreSQLContainer container;

//    public PostgreSQLTestContainer() {
//        super(POSTGRESQL_VERSION);
//    }

//    public static PostgreSQLContainer getInstance() {
//        if (container == null) {
//            container = new PostgreSQLTestContainer().withDatabaseName(DB_NAME);
//        }
//        return container;
//    }

//    @Override
    public void start() {
//        super.start();
//        System.setProperty("DB_URL", container.getJdbcUrl());
//        System.setProperty("DB_USERNAME", container.getUsername());
//        System.setProperty("DB_PASSWORD", container.getPassword());
    }

//    @Override
    public void stop()
        {
    }
}