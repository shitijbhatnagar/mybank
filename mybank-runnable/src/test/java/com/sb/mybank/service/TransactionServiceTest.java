package com.sb.mybank.service;

import com.sb.mybank.model.objects.TransactionDTO;
import com.sb.mybank.model.Transaction;
import com.sb.mybank.repository.TransactionRepository;
import com.sb.mybank.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@Profile("test")
@Slf4j
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    //The object to be tested is the AccountTransactionService and it's dependency (DAO class) will be mocked
    @InjectMocks
    TransactionServiceImpl accountTransactionService;

    //The service to be mocked - as the DB operations will not be invoked by the AccountTransactionService but mocked
    @Mock
    TransactionRepository accountTransactionRepository;

    @Test
    //Service & Repository test - get transactions
    void ut_findAll()
    {
        log.debug("TransactionServiceTest: @ut_findAll");
        when(accountTransactionRepository.findAll()).thenReturn(DataUtils.getMockTransactionEntityList());
        log.debug("TransactionServiceTest: @ut_findAll - Mock account transaction list returned");
        assertEquals(3, accountTransactionService.findAll().size());
        log.debug("TransactionServiceTest: @ut_findAll - assertEquals check executed");
        log.info("TransactionServiceTest: @ut_findAll executed successfully");
    }

    @Test
    //Service & Repository test - create new transaction
    void ut_createTransaction()
    {
        log.debug("TransactionServiceTest: @ut_createTransaction");

        TransactionDTO inputDTO = DataUtils.getMockTransactionDTO(); //what comes from API consumer
        Transaction inputEntity = DataUtils.convertDTOToEntity(inputDTO); //what is given to Repository

        //Using lenient() since Mockito is matching Entity object signatures/addresses & they can be different but same data
        lenient().when(accountTransactionRepository.save(inputEntity)).thenReturn(inputEntity);
        log.debug("TransactionServiceTest: @ut_createTransaction - Mock account transaction created / returned");

        TransactionDTO outputDTO = accountTransactionService.createInDB(inputDTO);
        assertEquals(inputDTO, outputDTO);
        log.debug("TransactionServiceTest: @ut_createTransaction - assertEquals check executed");
        log.info("TransactionServiceTest: @ut_createTransaction - executed successfully");
    }
}