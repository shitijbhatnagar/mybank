package com.sb.mybank.constants;

public interface APIEndPointsAndConstants
{
    public static String api_getCreateTransactions = "/transactions";
    public static String api_createTransaction = "/transactions";

    public static String api_publicAPIHostPort = "http://echo.jsontest.com";
    public static String api_publicAPIEndpoint = "/title/ipsum/content/dummydata";

    public static String const_wireMockHost = "localhost";

    public static int const_wireMockPort = 8090;

    public static String const_wireMockPreAPIURL = "http://" + const_wireMockHost + ":" + const_wireMockPort;
}