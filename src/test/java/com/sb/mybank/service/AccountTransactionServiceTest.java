package com.sb.mybank.service;

import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceTest {

    //The object to be tested is the AccountTransactionService and it's dependency (DAO class) will be mocked
    @InjectMocks
    AccountTransactionService accountTransactionService;

    //The service to be mocked - as the DB operations will not be invoked by the AccountTransactionService but mocked
    @Mock
    AccountTransactionDAO accountTransactionDAO;

    @Test
    //Service & Repository test - get transactions
    void ut_findAll()
    {
        log.debug("AccountTransactionServiceTest: @ut_findAll");

        when(accountTransactionDAO.findAll()).thenReturn(MockDataProvider.getMockTransactionList());
        log.debug("AccountTransactionServiceTest: @ut_findAll - Mock account transaction list returned");
        assertEquals(3, accountTransactionService.findAll().size());
        log.debug("AccountTransactionServiceTest: @ut_findAll - assertEquals check executed");
        log.info("AccountTransactionServiceTest: @ut_findAll executed successfully");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createTransaction()
    {
        log.debug("AccountTransactionServiceTest: @ut_createTransaction");

        AccountTransactionDTO inputDTO = MockDataProvider.getMockTransaction();

        when(accountTransactionDAO.createInDB(inputDTO)).thenReturn(inputDTO);
        log.debug("AccountTransactionServiceTest: @ut_createTransaction - Mock account transaction created / returned");
        assertEquals(inputDTO, accountTransactionService.createInDB(inputDTO));
        log.debug("AccountTransactionServiceTest: @ut_createTransaction - assertEquals check executed");
        log.info("AccountTransactionServiceTest: @ut_createTransaction - executed successfully");
    }
}