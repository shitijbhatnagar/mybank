/*
    This is the crude way of testing a Controller's method's (non-HTTP functionality) using JUnit
 */

package com.sb.mybank.web;

import com.sb.mybank.model.AccountTransactionDTO;
import com.sb.mybank.service.AccountTransactionService;
import com.sb.mybank.util.MockDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class) //needed along with @SpringBootTest to test the service and repository/DAO methods using mocking
@SpringBootTest //needed along with @RunWith to test the service and repository/DAO methods using mocking
class AccountTransactionControllerTest {

    @Autowired
    AccountTransactionController accountTransactionController;

    @MockBean
    AccountTransactionService accountTransactionService;

    @Test
    public void ut_testFindAll()
    {
        System.out.println("AccountTransactionControllerTest: ut_testFindAll");

        //When the service is called, DAO class should not be called and mocked data should be returned
        when(accountTransactionService.findAll()).thenReturn(MockDataProvider.getMockTransactionList());

        //Invoke the controller method (like any java class) to get a list of transactions
        List<AccountTransactionDTO> transactions = accountTransactionController.getTransactions();
        System.out.println("AccountTransactionControllerTest: ut_testFindAll - Mock account transaction created / returned");

        //Check expected no of mocked transactions are returned
        assertEquals(MockDataProvider.getMockTransactionList().size(), transactions.size());
        System.out.println("AccountTransactionControllerTest: ut_testFindAll - assertEquals check executed");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createTransaction()
    {
        System.out.println("AccountTransactionControllerTest: ut_createTransaction");

        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
        inputDTO.setId(UUID.randomUUID().toString());
        inputDTO.setUserId("mockUser20");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 20");
        inputDTO.setAmount(BigDecimal.valueOf(120));

        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
        System.out.println("AccountTransactionControllerTest: @createTransaction - Mock account transaction created / returned");

        assertEquals(inputDTO, accountTransactionController.createTransaction(inputDTO));
        assertEquals(inputDTO.getUserId(), accountTransactionController.createTransaction(inputDTO).getUserId());
        System.out.println("AccountTransactionControllerTest: @createTransaction - assertEquals check(s) executed");
    }
}