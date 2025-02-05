package com.sb.mybank.remote.test.execution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.MybankApplication;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.remote.config.RemoteTransactionConfig;
import com.sb.mybank.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.github.tomakehurst.wiremock.http.Body;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * This integration test uses Wiremock for local API testing
 * This is only intended to validate API functionality (/transactions end point) without hitting real API service
 */
@ActiveProfiles("remote")
@Profile("remote")
@ContextConfiguration(classes = {RemoteTransactionConfig.class})
@SpringBootTest(classes = MybankApplication.class) //Required for bootstrapping the entire Spring container
@Slf4j
public class RemoteTransactionAppITTest
{
    static String stubbedUrl;

    static WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate remoteRestTemplate;

    @BeforeAll
    static void setup()
    {
        stubbedUrl = "http://localhost:" +
                APIEndPointsAndConstants.const_wireMockPort +
                APIEndPointsAndConstants.api_getCreateTransactions;

        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("RemoteTransactionAppITTest: Wiremock started at port " + wireMockServer.port());
        log.info("RemoteTransactionAppITTest: Wiremock started at port " + wireMockServer.port());
    }

    @Test
    public void testGetTransactions() throws Exception
    {
        log.debug("RemoteTransactionAppITTest - Setting up necessary mocking for testGetTransactions()");

        stubFor(get(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .willReturn(ok())
                .willReturn(aResponse()
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")
                        .withResponseBody(Body.fromJsonBytes(objectMapper.writeValueAsBytes(
                                        DataUtils.getMockTransactionDTOList())))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<TransactionDTO> transactionDataList = List.of();
        ResponseEntity<List<TransactionDTO>> response;

        HttpEntity<List<TransactionDTO>> httpEntity = new HttpEntity<>(transactionDataList, headers);
        response = remoteRestTemplate.exchange(stubbedUrl, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<TransactionDTO>>() {
                });

        transactionDataList = response.getBody();

        assertEquals(transactionDataList.size(),3);
        log.debug("RemoteTransactionAppITTest: testGetTransactions - retrieval complete : " + transactionDataList.toString());
    }

    @Test
    public void testCreateTransaction() throws Exception
    {
        log.debug("RemoteTransactionAppITTest - Setting up necessary mocking for testCreateTransaction()");

        TransactionDTO transactionData = DataUtils.getTestTransactionDTO();

        stubFor(post(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions))
                .willReturn(ok())
                .willReturn(aResponse()
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")
                        .withResponseBody(Body.fromJsonBytes(objectMapper.writeValueAsBytes(
                                DataUtils.getTestCreatedTransactionDTO(transactionData))))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<TransactionDTO> response;
        HttpEntity<TransactionDTO> httpEntity = new HttpEntity<>(transactionData, headers);
        response = remoteRestTemplate.exchange(stubbedUrl, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<TransactionDTO>() {
                });

        TransactionDTO transactionCreated = response.getBody();

        assertEquals(transactionData.getReference(), transactionCreated.getReference());
        log.debug("RemoteTransactionAppITTest: testCreateTransaction - creation complete : " + transactionCreated.toString());
    }

    @Test
    public void testGetSingleTransaction() throws Exception
    {
        log.debug("RemoteTransactionAppITTest - Setting up necessary mocking for testGetSingleTransaction()");

        TransactionDTO transactionDto = DataUtils.getMockTransactionDTO();
        stubFor(get(urlPathEqualTo(APIEndPointsAndConstants.api_getCreateTransactions + "/" + transactionDto.getId()))
                .willReturn(ok())
                .willReturn(aResponse()
                        .withHeader("Content-Type",
                                "application/json;charset=UTF-8")
                        .withResponseBody(Body.fromJsonBytes(objectMapper.writeValueAsBytes(
                                transactionDto)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransactionDTO transactionData = new TransactionDTO();
        ResponseEntity<TransactionDTO> response;

        HttpEntity<TransactionDTO> httpEntity = new HttpEntity<>(transactionData, headers);
        response = remoteRestTemplate.exchange(stubbedUrl + "/" + transactionDto.getId(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<TransactionDTO>() {
                });

        transactionData = response.getBody();

        assertEquals(transactionData.getId(),transactionDto.getId());
        log.debug("RemoteTransactionAppITTest: testGetSingleTransaction - retrieval complete : " + transactionData.toString());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("RemoteTransactionAppITTest: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("RemoteTransactionAppITTest: Wiremock stopped");
    }
}