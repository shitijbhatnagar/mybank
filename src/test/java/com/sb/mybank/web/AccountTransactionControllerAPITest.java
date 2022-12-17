/*
    MockMvc based HTTP calls to Controller's end points - still unit testing only
 */

package com.sb.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.mybank.config.TestConfig;
import com.sb.mybank.model.AccountTransactionDTO;
import com.sb.mybank.service.AccountTransactionService;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AccountTransactionController.class) //needed to do HTTP test of a controller (and use mock data)
@Import(TestConfig.class)
public class AccountTransactionControllerAPITest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AccountTransactionService accountTransactionService;

    @Test
    //Invoke /transaction end pont to get mock data
    public void http_getTransactionsAPI() throws Exception
    {
        when(accountTransactionService.findAll()).thenReturn(MockDataProvider.getMockTransactionList());
        log.info("AccountTransactionControllerAPITest: Mock transaction data set for GET /transactions end point");

        log.info("AccountTransactionControllerAPITest: Transaction end point GET /transactions invoked");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

        log.info("AccountTransactionControllerAPITest: Transactions retrieved from GET /transactions");
    }

    @Test
    public void http_createEmployeeAPI() throws Exception
    {
        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
        inputDTO.setId(UUID.randomUUID().toString());
        inputDTO.setUserId("mockUser4");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 4");
        inputDTO.setAmount(BigDecimal.valueOf(80));

        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
        log.info("AccountTransactionControllerAPITest: NEW 01 Mock transaction data created for POST /transactions");

        log.info("AccountTransactionControllerAPITest: Transaction end point POST /transactions invoked");
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(objectMapper.writeValueAsString(inputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        log.info("AccountTransactionControllerAPITest: Successful transaction created from POST /transactions");

    }
}