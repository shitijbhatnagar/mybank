package com.sb.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.repository.TransactionRepository;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest //Required for bootstrapping the entire Spring container
@AutoConfigureMockMvc
public class TransactionControllerIntegrationIT
{
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SqlGroup({@Sql(value = "classpath:test-schema.sql", executionPhase = BEFORE_TEST_METHOD)})
    void int_http_createTransaction() throws Exception {

        TransactionDTO inputDTO = MockDataProvider.getTestTransactionDTO();
        log.debug("TransactionControllerIntegrationIT: @int_http_createTransaction 01 real transaction data created for POST /transactions");

        mockMvc.perform( MockMvcRequestBuilders
                        .post(APIEndPointsAndConstants.api_createTransaction)
                        .content(objectMapper.writeValueAsString(inputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("mockUser001"))
                .andDo(print());

        log.debug("TransactionControllerIntegrationIT: @int_http_createTransaction Transaction end point POST /transactions invoked");

        //3 records are inserted at startup via 'SeedTransationDataLoader' if profile is "dev" + data.sql, so a new
        // insert implies total count of records should be 3+1+1 = 5 else  it should be 2 value if profile is not "dev"
        assertEquals(5, transactionRepository.findAll().size());

        log.debug("TransactionControllerIntegrationIT: @int_http_createTransaction Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationIT: @int_http_createTransaction executed successfully");
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:test-schema.sql", executionPhase = BEFORE_TEST_METHOD)})
    void int_http_getTransactions() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .get(APIEndPointsAndConstants.api_createTransaction)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());

        log.debug("TransactionControllerIntegrationIT: @int_http_getTransactions Transaction end point POST /transactions invoked");

        //3 records are inserted at startup through 'SeedTransationDataLoader' if profile is "dev" + data.sql
        assertEquals(4, transactionRepository.findAll().size());

        log.debug("TransactionControllerIntegrationIT: @int_http_getTransactions Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationIT: @int_http_getTransactions executed successfully");
    }
}