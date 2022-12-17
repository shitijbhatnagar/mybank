package com.sb.mybank.service;

import com.sb.mybank.model.AccountTransactionDTO;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class) //needed along with @SpringBootTest to test the service and repository/DAO methods using mocking
@SpringBootTest //needed along with @RunWith to test the service and repository/DAO methods using mocking
class AccountTransactionServiceTest {

    //The object to be tested is the AccountTransactionService
    @Autowired
    AccountTransactionService accountTransactionService;

    //The service to be mocked - as the DB operations will not be invoked by the AccountTransactionService but mocked
    @MockBean
    AccountTransactionDAO accountTransactionDAO;

    @Test
    //Service & Repository test - get transactions
    void ut_findAll()
    {
        log.info("AccountTransactionServiceTest: @getTransactions");

        when(accountTransactionDAO.findAll()).thenReturn(MockDataProvider.getMockTransactionList());
        log.info("AccountTransactionServiceTest: @findAll - Mock account transaction list returned");
        assertEquals(3, accountTransactionService.findAll().size());
        log.info("AccountTransactionServiceTest: @findAll - assertEquals check executed");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createInDB()
    {
        log.info("AccountTransactionServiceTest: @createTransaction");

        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
        inputDTO.setId(UUID.randomUUID().toString());
        inputDTO.setUserId("mockUser4");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 4");
        inputDTO.setAmount(BigDecimal.valueOf(85));

        when(accountTransactionDAO.createInDB(inputDTO)).thenReturn(inputDTO);
        log.info("AccountTransactionServiceTest: @createTransaction - Mock account transaction created / returned");
        assertEquals(inputDTO, accountTransactionService.createInDB(inputDTO));
        log.info("AccountTransactionServiceTest: @createTransaction - assertEquals check executed");
    }
}