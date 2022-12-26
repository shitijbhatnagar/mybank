package com.sb.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.mybank.MybankApplication;
import com.sb.mybank.config.ContainersEnv;
import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.repository.TransactionRepository;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
@Testcontainers
@SpringBootTest(classes = MybankApplication.class) //Required for bootstrapping the entire Spring container
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTCIT extends ContainersEnv
{
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${test.mode}")
    private String testMode;

    @Before
    public void init()
    {
        //Validate the test mode set in application-test.properties and set it accordingly
        Optional<String> optional = Optional.ofNullable(testMode);
        if(optional.isPresent())
        {
            switch(testMode)
            {
                case APIEndPointsAndConstants.const_testModeIndividual:
                    log.debug("TransactionControllerIntegrationTCIT: @init - Integration Test Mode is found as INDIVIDUAL");
                    break;
                case APIEndPointsAndConstants.const_testModeAll:
                    log.debug("TransactionControllerIntegrationTCIT: @init - Integration Test Mode is found as ALL");
                    break;
            }
        }
        else
        {
            testMode=APIEndPointsAndConstants.const_testModeIndividual; //Individual (if no value is present)
            log.debug("TransactionControllerIntegrationTCIT: @init - Integration Test Mode is set to INDIVIDUAL");
        }
    }

    @Test
    void int_http_tc_createTransaction() throws Exception {

        TransactionDTO inputDTO = MockDataProvider.getTestTransactionDTO();
        log.debug("TransactionControllerIntegrationTCIT: @int_http_tc_createTransaction 01 real transaction data created for POST /transactions");

        mockMvc.perform( MockMvcRequestBuilders
                        .post(APIEndPointsAndConstants.api_createTransaction)
                        .content(objectMapper.writeValueAsString(inputDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("mockUser001"))
                .andDo(print());

        log.debug("TransactionControllerIntegrationTCIT: @int_http_tc_createTransaction Transaction end point POST /transactions invoked");

        //Individual test - assert value should be 2 (data.sql + new record being inserted)
        //Integration test - assert value should be 3 i.e. 2 above
        // + 1 record inserted in TransactionControllerIntegrationIT
        int assertValue = 2; //default will be INDIVIDUAL

        if(!testMode.equals(APIEndPointsAndConstants.const_testModeIndividual)) assertValue = 3;
        assertEquals(assertValue, transactionRepository.findAll().size());
        log.debug("TransactionControllerIntegrationTCIT: @int_http_tc_createTransaction Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationTCIT: @int_http_tc_createTransaction executed successfully");
    }

    @Test
    void int_http_tc_getTransactions() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .get(APIEndPointsAndConstants.api_createTransaction)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());

        log.debug("TransactionControllerIntegrationTCIT: @int_http_tc_getTransactions Transaction end point POST /transactions invoked");

        //Individual test - assert value should be 1 (data.sql)
        //Integration test - assert value should be 2 i.e. 1 above
        // + 1 record inserted in TransactionControllerIntegrationIT
        int assertValue = 1; //default will be INDIVIDUAL

        if(!testMode.equals(APIEndPointsAndConstants.const_testModeIndividual)) assertValue = 2;
        assertEquals(assertValue, transactionRepository.findAll().size());
        log.debug("TransactionControllerIntegrationTCIT: @int_http_tc_getTransactions Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationTCIT: @int_http_tc_getTransactions executed successfully");
    }
}