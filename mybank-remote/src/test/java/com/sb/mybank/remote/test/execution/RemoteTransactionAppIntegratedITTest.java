package com.sb.mybank.remote.test.execution;

import com.sb.mybank.MybankApplication;
import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.remote.config.RemoteTransactionConfig;
import com.sb.mybank.remote.service.RemoteTransactionAppService;
import com.sb.mybank.util.DataUtils;
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
 * The service end point should be functional for this test to succeed
 */
@ActiveProfiles("integrated")
@Profile("integrated")
@Slf4j
@ContextConfiguration(classes = {RemoteTransactionConfig.class})
@SpringBootTest(classes = MybankApplication.class) //Required for bootstrapping the entire Spring container
public class RemoteTransactionAppIntegratedITTest
{
    //This service points to the remote service URL (service end point) provided in property file
    @Autowired
    private RemoteTransactionAppService remoteTransactionAppService;

    @Test
    public void testGetTransactions() throws Exception
    {
        log.debug("RemoteTransactionAppIntegratedITTest - starting to retrieve transactions");
        List<TransactionDTO> response = getTransactions();
        log.debug("RemoteTransactionAppIntegratedITTest - no of transactions is " + response.size());

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
        log.debug("RemoteTransactionAppIntegratedITTest - starting to retrieve single transaction");
        TransactionDTO response = remoteTransactionAppService.retrieveTransaction(id);
        log.debug("RemoteTransactionAppIntegratedITTest - transaction detail is " + response.toString());

        //Validate the transaction retrieved
        assertEquals(response.getId(),id);
    }

    @Test
    public void testCreateTransaction() throws Exception {

        log.debug("RemoteTransactionAppIntegratedITTest - starting to create a transaction");
        TransactionDTO response = remoteTransactionAppService.createTransaction(DataUtils.getTestTransactionDTO());
        log.debug("RemoteTransactionAppIntegratedITTest - transaction detail is " + response.toString());

        //Validate the newly created transaction is not null
        assertNotNull(response.getId());
    }
}
