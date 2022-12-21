package com.sb.mybank.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@Slf4j
public class PublicServiceIT
{
    static WireMockServer wireMockServer;

    static PublicServiceImpl publicService;

    //Hit a mocked (public) API end point
    @Test
    public void wiremock_happy_ok_publicapi()
    {
        //Step 1 - Given : setup the API Stub
        log.debug("PublicServiceIT: wiremock_happy_ok_publicapi: Attempting stubbing");
        givenThat((any(anyUrl()).willReturn(ok())));
        log.debug("PublicServiceIT: wiremock_happy_ok_publicapi: Stubbing done successfully");

        //Step 2 - When: invoke the Public service/API (though mocked)
        boolean apiAvailability = publicService.IsPublicAPIAvailable(APIEndPointsAndConstants.api_publicAPIEndpoint);
        log.debug("PublicServiceIT: wiremock_happy_ok_publicapi: API Request executed");

        //Step 3 - Assert
        assertEquals(true,apiAvailability);
        log.debug("PublicServiceIT: wiremock_happy_ok_publicapi: Asserts executed successfully");
        log.info("PublicServiceIT: wiremock_happy_ok_publicapi executed successfully");
    }

    @BeforeAll
    static void setup()
    {
        //Setup the PublicService with same local host / URL as Mock Server
        publicService = new PublicServiceImpl();
        publicService.setHost(APIEndPointsAndConstants.const_wireMockPreAPIURL);
        log.debug("PublicServiceIT: Public Service is setup for local");

        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("PublicServiceIT: Wiremock started at port " + wireMockServer.port());
        log.info("PublicServiceIT: Wiremock started at port " + wireMockServer.port());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("PublicServiceIT: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("PublicServiceIT: Wiremock stopped");
    }
}
