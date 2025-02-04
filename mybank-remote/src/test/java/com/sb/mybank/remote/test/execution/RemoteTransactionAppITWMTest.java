package com.sb.mybank.remote.test.execution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.MybankApplication;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.remote.config.RemoteTransactionConfig;
import com.sb.mybank.remote.service.RemoteTransactionAppService;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertNotEquals;
import com.github.tomakehurst.wiremock.http.Body;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;

/**
 * This integration test uses Wiremock for local API testing
 */
@ActiveProfiles("remote")
@Profile("remote")
@Slf4j
@ContextConfiguration(classes = {RemoteTransactionConfig.class})
@SpringBootTest(classes = MybankApplication.class) //Required for bootstrapping the entire Spring container
public class RemoteTransactionAppITWMTest
{
    static WireMockServer wireMockServer;

    @Autowired
    private RemoteTransactionAppService remoteTransactionAppService;

    @Autowired
    private ObjectMapper testObjectMapper;

    @BeforeAll
    static void setup()
    {
        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("RemoteTransactionAppITWMTest: Wiremock started at port " + wireMockServer.port());
        log.info("RemoteTransactionAppITWMTest: Wiremock started at port " + wireMockServer.port());
    }

    @Test
    public void testGetTransactions() throws Exception
    {
        log.debug("RemoteTransactionAppITWMTest - Setting up necessary mocking for testGetTransactions()");

        wireMockServer.givenThat(get(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .willReturn(ok())
                .willReturn(aResponse()
                        .withResponseBody(new Body(testObjectMapper.writeValueAsBytes(
                                                MockDataProvider.getMockTransactionDTOList())))));

        //TBD - using mocking and right data verification

//        log.debug("RemoteTransactionAppITWMTest - starting to retrieve transactions");
//        List<TransactionDTO> response = remoteTransactionAppService.retrieveTransactions();
//        log.debug("RemoteTransactionAppITWMTest - no of transactions is " + response.size());
//
//        //Validate the no of transactions retrieved - should be above 0
//        assertNotEquals(response.size(),0);

        //Invoke the Wiremock stub
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        Request request = new Request.Builder()
//                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
//                .method("GET", null)
//                .build();
//        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request prepared");
//
//        //Step 2 - When: invoke the GET /transactions end point
//        Response response = client.newCall(request).execute();
//        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request executed");
//
//        String apiResponse = response.body().string();
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("RemoteTransactionAppITTest: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("RemoteTransactionAppITTest: Wiremock stopped");
    }
}
