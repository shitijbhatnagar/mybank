package com.sb.mybank.constants;

public interface APIEndPointsAndConstants
{
    public static String api_getCreateTransactions = "/transactions";
    public static String api_createTransaction = "/transactions";

    public static String api_publicAPIHostPort = "https://dummyjson.com";
    public static String api_publicAPIEndpoint = "/todos";

    public static String const_uuid = "dc82a225-bfff-4bb7-b13b-cdba279a60b7";
    public static String const_wireMockHost = "localhost";

    public static int const_wireMockPort = 8090;

    public static String const_wireMockPreAPIURL = "http://" + const_wireMockHost + ":" + const_wireMockPort;

    public static String const_PostgreSQL_Test_DB_Name = "test";

    public static String const_PostgreSQL_Version = "postgres:11.1";

    public static String const_testModeIndividual = "0";

    public static String const_testModeAll = "1";
}