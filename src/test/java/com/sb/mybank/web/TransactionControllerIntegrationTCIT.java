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
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("testcontainer")
@Slf4j
@Testcontainers
@SpringBootTest //Required for bootstrapping the entire Spring container
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTCIT
{
    //Define the PostgreSQL container
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withPassword("inmemory")
            .withUsername("inmemory");

    //Defne the properties of PostgreSQL DB (in container)
    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //@Test
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

        //3 records are inserted at startup through 'SeedTransationDataLoader' if profile is "dev", so this new insert
        //implies total count of records should be 3+1 = 4 otherwise it should be 1 value if profile is not "dev"
        assertEquals(4, transactionRepository.findAll().size());

        log.debug("TransactionControllerIntegrationIT: @int_http_createTransaction Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationIT: @int_http_createTransaction executed successfully");
    }

    //@Test
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

        //3 records are inserted at startup through 'SeedTransationDataLoader' if profile is "dev"
        assertEquals(3, transactionRepository.findAll().size());

        log.debug("TransactionControllerIntegrationIT: @int_http_getTransactions Successful transaction created from POST /transactions");
        log.info("TransactionControllerIntegrationIT: @int_http_getTransactions executed successfully");
    }
}
