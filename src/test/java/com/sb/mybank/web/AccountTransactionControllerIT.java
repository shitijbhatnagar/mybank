package com.sb.mybank.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.config.TestConfig;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import okhttp3.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Slf4j
@Import(TestConfig.class)
@TestPropertySource("classpath:test-application.properties")
public class AccountTransactionControllerIT
{
    static WireMockServer wireMockServer;

    String mockTransactionJSON = "[{\\\"id\":\\\"dc82a225-bfff-4bb7-b13b-cdba279a60b7\\\",\\\"userId\\\":\\\"mockUser1\\\",\\\"timestamp\\\":\\\"2022-12-18T21:49+0530\\\",\\\"reference\\\":\\\"mock sample 1\\\",\\\"amount\\\":80}]";

//    @Autowired
//    ObjectMapper objectMapper;

    //JUnit test case
    @Test
    public void wiremock_getTransactions() throws Exception
    {
        /*
        Test scenario is arranged in the format of Given — When — Then that makes the test flow easily readable:

        Step 1 — Given — Set up WireMock stub for Get Transactions API
        Step 2 — When — Invoke the Get Transactions API client to fetch a transactions
        Step 3 — Then — Verify the retrieved transactions or Status
        Step 4 - Use verify() method to check if WireMock server received the request for transactions data retrieval
        */

        //Step 1 - Given : setup the API Stub
//        stub_getTransactions();

        //Define stub
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Attempting stubbing");
        //stubFor(get("http://localhost:" + wireMockRule.port() + APIEndPointsAndConstants.api_getTransactions).willReturn(ok()));
        givenThat((any(anyUrl()).willReturn(ok())));
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
       OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://localhost:" + wireMockServer.port() + APIEndPointsAndConstants.api_getTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request executed");

//        String apiResponse = response.body().string();
//        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API data returned - " + apiResponse);

        //Retrieve the list of transactions from the response
        // assert the response objectMapper.writeValueAsString(MockDataProvider.getMockTransactionList())
//        assertEquals(mockTransactionJSON,response.body().string());
        assertEquals(true,response.isSuccessful());
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Asserts executed successfully");
    }

//    private void stub_getTransactions() throws JsonProcessingException
//    {
//        log.debug("AccountTransactionControllerIT: stub_getTransactions: URL " + "http://localhost:8081" + APIEndPointsAndConstants.api_getTransactions);
//
//        //Stubbing the "/transactions" end point to return mock transactions
//        //"http://localhost:8081" + APIEndPointsAndConstants.api_getTransactions
//        //stubFor(get(urlEqualTo(APIEndPointsAndConstants.api_getTransactions))
//                givenThat(get(urlEqualTo(APIEndPointsAndConstants.api_getTransactions))
////                        givenThat(get(urlEqualTo("http://localhost:8081" + APIEndPointsAndConstants.api_getTransactions))
//                .willReturn(aResponse().withStatus(200)
//                        .withBody(mockTransactionJSON)));
//        log.debug("AccountTransactionControllerIT: stub_getTransactions: /transactions end point stubbed");
//    }

    @BeforeAll
    static void setup()
    {
        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.wireMockPort);
        configureFor("localhost", APIEndPointsAndConstants.wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("AccountTransactionControllerIT: Wiremock started at port " + wireMockServer.port());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("AccountTransactionControllerIT: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("AccountTransactionControllerIT: Wiremock stopped");
    }
}