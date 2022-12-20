/*
    This is the crude way of testing a Controller's method's (non-HTTP functionality) using JUnit
 */

package com.sb.mybank.web;

import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.service.AccountTransactionService;
import com.sb.mybank.service.AccountTransactionServiceImpl;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AccountTransactionControllerTest {
    @InjectMocks
    AccountTransactionController accountTransactionController;
    @Mock
    AccountTransactionServiceImpl accountTransactionService;
    @Test
    public void ut_testFindAll()
    {
        log.debug("AccountTransactionControllerTest: @ut_testFindAll");

        //When the service is called, DAO class should not be called and mocked data should be returned
        when(accountTransactionService.findAll()).thenReturn(MockDataProvider.getMockTransactionList());

        //Invoke the controller method (like any java class) to get a list of transactions
        List<AccountTransactionDTO> transactions = accountTransactionController.getTransactions();
        log.debug("AccountTransactionControllerTest: @ut_testFindAll - Mock account transaction created / returned");

        //Check expected no of mocked transactions are returned
        assertEquals(MockDataProvider.getMockTransactionList().size(), transactions.size());
        log.debug("AccountTransactionControllerTest: @ut_testFindAll - assertEquals check executed");
        log.info("AccountTransactionControllerTest: @ut_testFindAll executed successfully");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createTransaction()
    {
        log.debug("AccountTransactionControllerTest: @ut_createTransaction");

        AccountTransactionDTO inputDTO = MockDataProvider.getMockTransaction();

        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
        log.debug("AccountTransactionControllerTest: @ut_createTransaction - Mock account transaction created / returned");

        assertEquals(inputDTO, accountTransactionController.createTransaction(inputDTO));
        assertEquals(inputDTO.getUserId(), accountTransactionController.createTransaction(inputDTO).getUserId());
        log.debug("AccountTransactionControllerTest: @ut_createTransaction - assertEquals check(s) executed");
        log.info("AccountTransactionControllerTest: @ut_createTransaction executed successfully");
    }
}