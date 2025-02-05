/*
    This is the crude way of testing a Controller's method's (non-HTTP functionality) using JUnit
 */

package com.sb.mybank.web;

import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.service.TransactionServiceImpl;
import com.sb.mybank.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @InjectMocks
    TransactionController accountTransactionController;
    @Mock
    TransactionServiceImpl accountTransactionService;
    @Test
    public void ut_testFindAll()
    {
        log.debug("TransactionControllerTest: @ut_testFindAll");

        //When the service is called, DAO class should not be called and mocked data should be returned
        when(accountTransactionService.findAll()).thenReturn(DataUtils.getMockTransactionDTOList());

        //Invoke the controller method (like any java class) to get a list of transactions
        List<TransactionDTO> transactions = accountTransactionController.getTransactions();
        log.debug("TransactionControllerTest: @ut_testFindAll - Mock account transaction created / returned");

        //Check expected no of mocked transactions are returned
        assertEquals(DataUtils.getMockTransactionDTOList().size(), transactions.size());
        log.debug("TransactionControllerTest: @ut_testFindAll - assertEquals check executed");
        log.info("TransactionControllerTest: @ut_testFindAll executed successfully");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createTransaction()
    {
        log.debug("TransactionControllerTest: @ut_createTransaction");

        TransactionDTO inputDTO = DataUtils.getMockTransactionDTO();

        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
        log.debug("TransactionControllerTest: @ut_createTransaction - Mock account transaction created / returned");

        assertEquals(inputDTO, accountTransactionController.createTransaction(inputDTO));
        assertEquals(inputDTO.getUserId(), accountTransactionController.createTransaction(inputDTO).getUserId());
        log.debug("TransactionControllerTest: @ut_createTransaction - assertEquals check(s) executed");
        log.info("TransactionControllerTest: @ut_createTransaction executed successfully");
    }
}