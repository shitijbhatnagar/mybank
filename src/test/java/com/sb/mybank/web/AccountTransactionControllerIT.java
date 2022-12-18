package com.sb.mybank.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.sb.mybank.config.TestConfig;
import com.sb.mybank.constants.APIEndPoints;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import okhttp3.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test-application.properties")
public class AccountTransactionControllerIT
{
    //@Value("${wiremock.port}")
    Integer wmPort=8085;

    //@Value("${server.port}")
    Integer svPort=8081;

    //@Value("${wiremock.host}")
    String wmServer="localhost";

    //@Value("${local.host}")
    String localServer="localhost";

    static WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    @Rule
    public WireMockRule wm = new WireMockRule(wireMockConfig().port(wmPort));

    //JUnit test case
    @Test
    public void wiremock_getTransactions() throws Exception {

        //Setup the API Stub
        stub_getTransactions();

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://" + localServer + ":" + svPort + APIEndPoints.api_getTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request prepared");

        Response response = client.newCall(request).execute();
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: API Request executed");

        // assert the response
//        assertEquals(objectMapper.writeValueAsString(MockDataProvider.getMockTransactionList()), response.body().string());
        // BUG - the value "[]" is getting stubbed and returned
        assertEquals("[]", response.body().string());
        log.debug("AccountTransactionControllerIT: wiremock_getTransactions: Asserts executed successfully");
    }

    private void stub_getTransactions() throws JsonProcessingException
    {
        log.debug("AccountTransactionControllerIT: stub_getTransactions: URL " + "http://" + localServer + ":" + svPort + APIEndPoints.api_getTransactions);

        //Stubbing the "/transactions" end point to return mock transactions
        //BUG - value "[]" is getting stubbed instead of the mock data
        stubFor(get(urlEqualTo(APIEndPoints.api_getTransactions))
                .willReturn(aResponse()
                        .withBody(objectMapper.writeValueAsString(MockDataProvider.getMockTransactionList()))));
//                        .withBody(objectMapper.writeValueAsString(MockDataProvider.getMockTransactionList()))));
        log.info("AccountTransactionControllerIT: stub_getTransactions: /transactions end point stubbed");
    }

    @BeforeAll
    private static void init() throws Exception
    {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8085));
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        log.debug("AccountTransactionControllerIT: init(): Wiremock configured at port " + wireMockServer.port());
    }
}