package com.sb.mybank.containers;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import org.testcontainers.containers.PostgreSQLContainer;

//Create the PostgreSQL Test container
public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer>
{
    private static PostgreSQLContainer container;

    private PostgreSQLTestContainer() {
        super(APIEndPointsAndConstants.const_PostgreSQL_Version);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLTestContainer().withDatabaseName(APIEndPointsAndConstants.const_PostgreSQL_Test_DB_Name);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop()
    {
        //No special action as yet
        super.stop();
    }
}