/*
Test scenario is arranged in the format of Given — When — Then that makes the test flow easily readable:

Step 1 — Given — Set up WireMock stub for Get Transactions API
Step 2 — When — Invoke the Get Transactions API client to fetch a transactions
Step 3 — Then — Assert/Verify the retrieved transactions or Status
*/

package com.sb.mybank.web;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import okhttp3.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@Slf4j
public class AccountTransactionControllerIT
{
    static WireMockServer wireMockServer;

    @Test
    public void wiremock_happy_ok_getTransactions() throws Exception
    {
        //Step 1 - Given : setup the API Stub
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Attempting stubbing");
        givenThat((any(anyUrl()).willReturn(ok())));
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
       OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getTransactions + APIEndPointsAndConstants.api_getTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request executed");

        //Step 3 - Assert
        assertEquals(true,response.isSuccessful());
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Asserts executed successfully");
        log.info("AccountTransactionControllerIT: wiremock_getTransactions executed successfully");
    }

    //Hit the /transactions end point but mock the response and verify mocked data
    @Test
    public void wiremock_happy_requestmatch_getTransactions() throws Exception
    {
        //Define stub
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Attempting stubbing");
        givenThat((any(anyUrl()).willReturn(ok())
                        .willReturn(aResponse().withBody(MockDataProvider.getMockTransactionJSON()))));
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request executed");

        String apiResponse = response.body().string();
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API data returned - " + apiResponse);

        //Retrieve the list of transactions from the response and match against the mocked data expected
        assertEquals(true,response.isSuccessful());
        assertEquals(MockDataProvider.getMockTransactionJSON(),apiResponse);
        log.debug("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Asserts executed successfully");
        log.info("AccountTransactionControllerIT: @wiremock_happy_requestmatch_getTransactions executed successfully");
    }

    @BeforeAll
    static void setup()
    {
        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("AccountTransactionControllerIT: Wiremock started at port " + wireMockServer.port());
        log.info("AccountTransactionControllerIT: Wiremock started at port " + wireMockServer.port());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("AccountTransactionControllerIT: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("AccountTransactionControllerIT: Wiremock stopped");
    }
}