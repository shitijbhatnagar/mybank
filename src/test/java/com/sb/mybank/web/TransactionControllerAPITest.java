/*
    MockMvc based HTTP calls to Controller's end points - still unit testing + Spring (not an integration test as such)
 */

package com.sb.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.mybank.config.TestConfig;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.service.TransactionService;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
@WebMvcTest(controllers = TransactionController.class) //needed to do HTTP test of a controller (and use mock data)
@Import(TestConfig.class)
public class TransactionControllerAPITest
{
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    private TransactionService accountTransactionService;

    @Test
    //Invoke "/transactions" end pont to get mock data
    public void http_getTransactionsAPI() throws Exception
    {
        when(accountTransactionService.findAll()).thenReturn(MockDataProvider.getMockTransactionDTOList());
        log.debug("TransactionControllerAPITest: @http_getTransactionsAPI Mock transaction data set for GET /transactions end point");

        log.debug("TransactionControllerAPITest: @http_getTransactionsAPI Transaction end point GET /transactions invoked");
        mockMvc.perform(MockMvcRequestBuilders
                        .get(APIEndPointsAndConstants.api_getCreateTransactions)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

        log.debug("TransactionControllerAPITest: @http_getTransactionsAPI Transactions retrieved from GET /transactions");
        log.info("TransactionControllerAPITest: @http_getTransactionsAPI executed successfully");
    }

    @Test
    public void http_createTransactionAPI() throws Exception
    {
        TransactionDTO inputDTO = MockDataProvider.getMockTransactionDTO();

        when(accountTransactionService.createInDB(inputDTO)).thenReturn(inputDTO);
        log.debug("TransactionControllerAPITest: @http_createTransactionAPI NEW 01 Mock transaction data created for POST /transactions");

        log.debug("TransactionControllerAPITest: @http_createTransactionAPI Transaction end point POST /transactions invoked");
        mockMvc.perform( MockMvcRequestBuilders
                        .post(APIEndPointsAndConstants.api_createTransaction)
                        .content(objectMapper.writeValueAsString(inputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        log.debug("TransactionControllerAPITest: @http_createTransactionAPI Successful transaction created from POST /transactions");
        log.info("TransactionControllerAPITest: @http_createTransactionAPI executed successfully");
    }
}