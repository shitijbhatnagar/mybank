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
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
public class TransactionControllerIT
{
    static WireMockServer wireMockServer;

    //Hit the mocked GET /transactions end point and check if call was successful
    @Test
    public void wiremock_happy_ok_getTransactions() throws Exception
    {
        //Step 1 - Given : setup the API Stub
        log.debug("TransactionControllerIT: wiremock_getTransactions: Attempting stubbing");
        givenThat(get(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)).willReturn(ok()));
        log.debug("TransactionControllerIT: wiremock_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
       OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("TransactionControllerIT: wiremock_getTransactions: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("TransactionControllerIT: wiremock_getTransactions: API Request executed");

        //Step 3 - Assert & verify
        assertEquals(true,response.isSuccessful());
        verify(getRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)));
        log.debug("TransactionControllerIT: wiremock_getTransactions: Asserts executed successfully");
        log.info("TransactionControllerIT: wiremock_getTransactions executed successfully");
    }

    //Hit the mocked GET /transactions end point, mock the response and verify mocked data
    @Test
    public void wiremock_happy_dataMatch_getTransactions() throws Exception
    {
        //Define stub
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Attempting stubbing");
                givenThat(get(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)).willReturn(ok())
                        .willReturn(aResponse().withBody(MockDataProvider.getMockTransactionJSON())));
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Stubbing done successfully");

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
                .method("GET", null)
                .build();
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API Request executed");

        String apiResponse = response.body().string();
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: API data returned - " + apiResponse);

        //Step 3 - Assert & verify
        //Retrieve the list of transactions from the response and match against the mocked data expected
        assertEquals(true,response.isSuccessful());
        assertEquals(MockDataProvider.getMockTransactionJSON(),apiResponse);
        verify(getRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)));
        log.debug("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions: Asserts executed successfully");
        log.info("TransactionControllerIT: @wiremock_happy_requestmatch_getTransactions executed successfully");
    }

    //Hit the mocked POST /transactions end point, mock the response and verify mocked data
    @Test
    public void wiremock_happy_dataMatch_createTransaction() throws Exception
    {
        //Define stub
        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: Attempting stubbing");
        givenThat(post(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .withRequestBody(equalToJson(MockDataProvider.getMockCreateTransactionRequestJSON()))
                .willReturn(ok())
                .willReturn(aResponse().withBody(MockDataProvider.getMockCreateTransactionResponseJSON())));

        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: Stubbing done successfully");

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType JSON = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(JSON, MockDataProvider.getMockCreateTransactionRequestJSON());
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
                .post(requestBody)
                .build();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: API Request executed");
        String apiResponse = response.body().string();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: API data returned - " + apiResponse);

        //Step 3 - Assert & verify
        //Retrieve the created transaction from the response and match against the mocked data expected
        assertEquals(true,response.isSuccessful());
        assertEquals(MockDataProvider.getMockCreateTransactionResponseJSON(),apiResponse);
        verify(postRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)));
        log.debug("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction: Asserts executed successfully");
        log.info("TransactionControllerIT: @wiremock_happy_dataMatch_createTransaction executed successfully");
    }

    //Negative test
    @Test
    public void wiremock_happy_dataMismatch_createTransaction() throws Exception
    {
        //Define stub
        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: Attempting stubbing");
        givenThat(post(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .withRequestBody(equalToJson(MockDataProvider.getMockCreateTransactionRequestJSON()))
                .willReturn(ok())
                //below return data does not contain the "id" field, so that is missing
                .willReturn(aResponse().withBody(MockDataProvider.getMockCreateTransactionRequestJSON())));

        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: Stubbing done successfully");

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType JSON = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(JSON, MockDataProvider.getMockCreateTransactionRequestJSON());
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
                .post(requestBody)
                .build();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: API Request executed");
        String apiResponse = response.body().string();
        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: API data returned - " + apiResponse);

        //Step 3 - Assert & verify
        assertNotEquals(MockDataProvider.getMockCreateTransactionResponseJSON(),apiResponse);
        verify(postRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)));
        log.debug("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction: Asserts executed successfully");
        log.info("TransactionControllerIT: @wiremock_happy_dataMismatch_createTransaction executed successfully");
    }


    //Hit the mocked POST /transactions end point, match request against JSON fields, mock the response and verify mocked data
    @Test
    public void wiremock_happy_dataJsonMatch_createTransaction() throws Exception
    {
        //Define stub
        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: Attempting stubbing");
        givenThat(post(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                //any request sent to the API end point with JSON data comprising following 05 fields will be accepted
                .withRequestBody(matchingJsonPath("userId"))
                .withRequestBody(matchingJsonPath("timestamp"))
                .withRequestBody(matchingJsonPath("reference"))
                .withRequestBody(matchingJsonPath("amount"))
                .willReturn(ok())
                .willReturn(aResponse().withBody(MockDataProvider.getMockCreateTransactionResponseJSON())));

        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: Stubbing done successfully");

        //Invoke the Wiremock stub
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType JSON = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(JSON, MockDataProvider.getMockCreateTransactionRequestJSON());
        Request request = new Request.Builder()
                .url(APIEndPointsAndConstants.const_wireMockPreAPIURL + APIEndPointsAndConstants.api_getCreateTransactions) //hit the end point
                .post(requestBody)
                .build();
        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: API Request prepared");

        //Step 2 - When: invoke the GET /transactions end point
        Response response = client.newCall(request).execute();
        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: API Request executed");
        String apiResponse = response.body().string();
        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: API data returned - " + apiResponse);

        //Step 3 - Assert & verify
        //Retrieve the created transaction from the response and match against the mocked data expected
        assertEquals(true,response.isSuccessful());
        assertEquals(MockDataProvider.getMockCreateTransactionResponseJSON(),apiResponse);
        //Verify single call to end point
        verify(postRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions)));
        //Verify call to end point with expected input data
        verify(postRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .withRequestBody(equalToJson(MockDataProvider.getMockCreateTransactionRequestJSON())));
        log.debug("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction: Asserts executed successfully");
        log.info("TransactionControllerIT: @wiremock_happy_dataJsonMatch_createTransaction executed successfully");
    }

    @BeforeAll
    static void setup()
    {
        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("TransactionControllerIT: Wiremock started at port " + wireMockServer.port());
        log.info("TransactionControllerIT: Wiremock started at port " + wireMockServer.port());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("TransactionControllerIT: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("TransactionControllerIT: Wiremock stopped");
    }
}