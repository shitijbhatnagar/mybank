package com.sb.mybank.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.sb.mybank.config.TestConfig;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import okhttp3.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Slf4j
@Import(TestConfig.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test-application.properties")
public class AccountTransactionControllerIT
{
//    @Rule
//    public WireMockRule wireMockRule=new WireMockRule(options().dynamicPort().dynamicPort());

    static WireMockServer wireMockRule;

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
        Step 3 — Then — Verify the retrieved transactions
        Step 4 - Use verify() method to check if WireMock server received the request for transactions data retrieval
        */

        //Step 1 - Given : setup the API Stub
//        stub_getTransactions();

        //Define stub
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Attempting stubbing");
        //stubFor(get("http://localhost:" + wireMockRule.port() + APIEndPointsAndConstants.api_getTransactions).willReturn(ok()));
        stubFor(any(anyUrl()).willReturn(ok()));
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
       OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://localhost:" + wireMockRule.port() + APIEndPointsAndConstants.api_getTransactions) //hit the end point
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

    private void stub_getTransactions() throws JsonProcessingException
    {
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
    }

    @BeforeEach
    public void before()
    {
        wireMockRule = new WireMockServer(8090);
        configureFor("localhost", 8090 /*WireMockConfiguration.wireMockConfig().dynamicPort().dynamicPort().portNumber()*/);

        //wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockRule.start();
        log.debug("AccountTransactionControllerIT: Wiremock started at port " + wireMockRule.port());
    }
//
//    @After
//    public void after()
//    {
//        log.debug("AccountTransactionControllerIT: Wiremock stop being attempted at port " + wireMockRule.port());
//        wireMockRule.stop();
//        log.debug("AccountTransactionControllerIT: Wiremock stopped");
//    }
//
//    @AfterEach
//    public void reset() {
//        wireMockRule.resetAll();
//        log.debug("AccountTransactionControllerIT: Wiremock configuration reset");
//    }
}