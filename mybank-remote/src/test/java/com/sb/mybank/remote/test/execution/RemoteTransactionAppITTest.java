package com.sb.mybank.remote.test.execution;

import com.sb.mybank.MybankApplication;
import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.remote.config.RemoteTransactionConfig;
import com.sb.mybank.remote.service.RemoteTransactionAppService;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import static org.junit.Assert.*;

/**
 * This is a real integration test (like a consumer of the service would do) using the Remote library "mybank-remote"
 */
@ActiveProfiles("remote")
@Profile("remote")
@Slf4j
@ContextConfiguration(classes = {RemoteTransactionConfig.class})
@SpringBootTest(classes = MybankApplication.class) //Required for bootstrapping the entire Spring container
public class RemoteTransactionAppITTest
{
//    @Autowired
//    String transactionRemoteUrl;
    @Autowired
    private RemoteTransactionAppService remoteTransactionAppService;

//    @Autowired
//    private ObjectMapper testObjectMapper;
//
//    @Autowired
//    private RestTemplate remoteRestTemplate;

//    @Before
//    void setup()
//    {
//        //remoteTransactionAppService = new RemoteTransactionAppServiceImpl(transactionRemoteUrl, remoteRestTemplate);
//        //log.debug("RemoteTransactionAppITTest: remoteTransactionAppService re-created");
//    }

//    @Before
//    void setup()
//    {
//        log.debug("RemoteTransactionAppITTest: Setting up Wiremock server");
//
//        //Setup WireMock server (internally a Jetty server)
//        wireMockServer = new WireMockServer(APIEndPointsAndConstants.const_wireMockPort);
//        wireMockServer.resetMappings();
//        configureFor(APIEndPointsAndConstants.const_wireMockHost, APIEndPointsAndConstants.const_wireMockPort);
//
//        //Start the WireMock server
//        wireMockServer.start();
//        log.debug("RemoteTransactionAppITTest: Wiremock started at port " + wireMockServer.port());
//        log.info("RemoteTransactionAppITTest: Wiremock started at port " + wireMockServer.port());
//
//        log.debug("RemoteTransactionAppITTest: URL to hit is " + "http://localhost:" + wireMockServer.port() + "/transactions");
//        remoteTransactionAppService = new RemoteTransactionAppServiceImpl("http://localhost:" + wireMockServer.port() + "/transactions", remoteRestTemplate);
//    }

    @Test
    public void testGetTransactions() throws Exception
    {
        log.debug("RemoteTransactionAppITTest - starting to retrieve transactions");
        List<TransactionDTO> response = getTransactions();
        log.debug("RemoteTransactionAppITTest - no of transactions is " + response.size());

        //Validate the no of transactions retrieved - should be above 0
        assertNotEquals(response.size(),0);
    }

    private List<TransactionDTO> getTransactions() throws Exception
    {
        return remoteTransactionAppService.retrieveTransactions();
    }

    @Test
    public void testGetTransaction() throws Exception {

        String id = getTransactions().stream().findAny().stream().iterator().next().getId();
        log.debug("RemoteTransactionAppITTest - starting to retrieve single transaction");
        TransactionDTO response = remoteTransactionAppService.retrieveTransaction(id);
        log.debug("RemoteTransactionAppITTest - transaction detail is " + response.toString());

        //Validate the transaction retrieved
        assertEquals(response.getId(),id);
    }

    @Test
    public void testCreateTransaction() throws Exception {

        log.debug("RemoteTransactionAppITTest - starting to create a transaction");
        TransactionDTO response = remoteTransactionAppService.createTransaction(MockDataProvider.getTestTransactionDTO());
        log.debug("RemoteTransactionAppITTest - transaction detail is " + response.toString());

        //Validate the newly created transaction is not null
        assertNotNull(response.getId());
    }


//    @After
//    void cleanup()
//    {
//        log.debug("RemoteTransactionAppITTest: Wiremock stop being attempted at port " + wireMockServer.port());
//        wireMockServer.stop();
//        log.debug("RemoteTransactionAppITTest: Wiremock stopped");
//    }
}
