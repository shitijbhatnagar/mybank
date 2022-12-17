/*
    This is an alternative way of testing a Controller class - not necessary to use if testing is already done using MockMvc
 */

package com.sb.mybank.web;

import com.sb.mybank.model.AccountTransactionDTO;
import com.sb.mybank.service.AccountTransactionService;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AccountTransactionControllerMethodsTest {

    @InjectMocks //This injects the controller
    AccountTransactionController accountTransactionController;

    @Mock //(instead of @MockBean)
    AccountTransactionService accountTransactionService;

    @Test
    public void ut_method_testFindAll() {
        log.info("AccountTransactionControllerMethodsTest: ut_testFindAll");

        //When the service is called, DAO class should not be called and mocked data should be returned
        when(accountTransactionService.findAll()).thenReturn(MockDataProvider.getMockTransactionList());

        //Invoke the controller method (like any java class) to get a list of transactions
        List<AccountTransactionDTO> transactions = accountTransactionController.getTransactions();
        log.info("AccountTransactionControllerTest: ut_method_testFindAll - Mock account transaction created / returned");

        //Check expected no of mocked transactions are returned and basic data matching
        assertEquals(MockDataProvider.getMockTransactionList().size(), transactions.size());
        assertThat(transactions.get(0).getUserId()).isEqualTo(MockDataProvider.getMockTransactionList().get(0).getUserId());
        assertThat(transactions.get(1).getUserId()).isEqualTo(MockDataProvider.getMockTransactionList().get(1).getUserId());
        assertThat(transactions.get(2).getUserId()).isEqualTo(MockDataProvider.getMockTransactionList().get(2).getUserId());
        log.info("AccountTransactionControllerMethodsTest: ut_method_testFindAll - assertEquals check executed");
    }
}

// COMMENTED BELOW - FOR FUTURE USE

//    @Test
//    //Controller method test via HTTP Request classes - create new transaction
//    void ut_method_createTransaction()
//    {
//        System.out.println("AccountTransactionControllerMethodsTest: ut_method_createTransaction");
//
//        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
//        inputDTO.setId(UUID.randomUUID().toString());
//        inputDTO.setUserId("mockUser20");
//        inputDTO.setTimestamp(ZonedDateTime.now());
//        inputDTO.setReference("mock sample 20");
//        inputDTO.setAmount(BigDecimal.valueOf(120));
//
//        /*
//
//        The mock HTTP classes should be used if ResponseEntity is being returned from REST API instead of a standard DTO
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        //This call should be added followed by status checks against the ResponseEntity e.g. status
//        ResponseEntity<Object> responseEntity = accountTransactionController.createTransaction(inputDTO);
//         */
//
//        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
//        System.out.println("AccountTransactionControllerMethodsTest: @createTransaction - Mock account transaction created / returned");
//
//        //Following asserts perform the operation using the Controller
//        assertEquals(inputDTO, accountTransactionController.createTransaction(inputDTO));
//        assertEquals("mockUser20", accountTransactionController.createTransaction(inputDTO).getUserId());
//        System.out.println("AccountTransactionControllerMethodsTest: ut_method_createTransaction - assertEquals check(s) executed");
//    }