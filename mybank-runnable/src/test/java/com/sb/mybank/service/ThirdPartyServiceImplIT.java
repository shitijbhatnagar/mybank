package com.sb.mybank.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
public class ThirdPartyServiceImplIT
{
    static WireMockServer wireMockServer;

    static ThirdPartyServiceImpl thirdPartyService;

    //Hit a mocked (public) API end point
    @Test
    public void wiremock_happy_ok_publicapi()
    {
        //Step 1 - Given : setup the API Stub
        log.debug("ThirdPartyServiceIT: wiremock_happy_ok_publicapi: Attempting stubbing");
        givenThat((any(anyUrl()).willReturn(ok())));
        log.debug("ThirdPartyServiceIT: wiremock_happy_ok_publicapi: Stubbing done successfully");

        //Step 2 - When: invoke the Public service/API (though mocked)
        boolean apiAvailability = thirdPartyService.IsServiceAvailable(APIEndPointsAndConstants.api_publicAPIEndpoint);
        log.debug("ThirdPartyServiceIT: wiremock_happy_ok_publicapi: API Request executed");

        //Step 3 - Assert & verify
        assertEquals(true,apiAvailability);
        verify(getRequestedFor(urlEqualTo(APIEndPointsAndConstants.api_publicAPIEndpoint)));
        log.debug("ThirdPartyServiceIT: wiremock_happy_ok_publicapi: Asserts executed successfully");
        log.info("ThirdPartyServiceIT: wiremock_happy_ok_publicapi executed successfully");
    }

    //Hit a public API end point
    @Test
    public void happy_ok_publicapi()
    {
        //Step 1 - When: invoke the Public service/API
        log.debug("ThirdPartyServiceIT: happy_ok_publicapi: Attempting API Request");

        //Wiremock is not to be used, hence the service should point to the real third party service
        thirdPartyService.setHost(APIEndPointsAndConstants.api_publicAPIHostPort);

        boolean apiAvailability = thirdPartyService.IsServiceAvailable(APIEndPointsAndConstants.api_publicAPIEndpoint);
        log.debug("ThirdPartyServiceIT: happy_ok_publicapi: API Request executed");

        //Step 2 - Assert & verify
        assertEquals(true,apiAvailability);
        log.debug("ThirdPartyServiceIT: happy_ok_publicapi: Asserts executed successfully");
        log.info("ThirdPartyServiceIT: happy_ok_publicapi executed successfully");
    }

    @BeforeAll
    static void setup()
    {
        //Setup the PublicService with same local host / URL as Mock Server
        thirdPartyService = new ThirdPartyServiceImpl();
        thirdPartyService.setHost(APIEndPointsAndConstants.const_wireMockPreAPIURL);
        log.debug("ThirdPartyServiceIT: Public Service is setup for local");

        //Setup WireMock server (internally a Jetty server)
        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);

        //Start the WireMock server
        wireMockServer.start();
        log.debug("ThirdPartyServiceIT: Wiremock started at port " + wireMockServer.port());
        log.info("ThirdPartyServiceIT: Wiremock started at port " + wireMockServer.port());
    }

    @AfterAll
    static void cleanup()
    {
        log.debug("ThirdPartyServiceIT: Wiremock stop being attempted at port " + wireMockServer.port());
        wireMockServer.stop();
        log.debug("ThirdPartyServiceIT: Wiremock stopped");
    }
}