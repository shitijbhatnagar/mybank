package com.sb.mybank.service;

import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.model.AccountTransaction;
import com.sb.mybank.repository.AccountTransactionRepository;
import com.sb.mybank.util.MockDataProvider;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceTest{

    //The object to be tested is the AccountTransactionService and it's dependency (DAO class) will be mocked
    @InjectMocks
    AccountTransactionServiceImpl accountTransactionService;

    //The service to be mocked - as the DB operations will not be invoked by the AccountTransactionService but mocked
    @Mock
    AccountTransactionRepository accountTransactionRepository;

    @Test
    //Service & Repository test - get transactions
    void ut_findAll()
    {
        log.debug("AccountTransactionServiceTest: @ut_findAll");
        when(accountTransactionRepository.findAll()).thenReturn(MockDataProvider.getMockTransactionEntityList());
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

        AccountTransactionDTO inputDTO = MockDataProvider.getMockTransactionDTO(); //what comes from API consumer
        AccountTransaction inputEntity = MockDataProvider.convertDTOToEntity(inputDTO); //what is given to Repository

        //Using lenient() since Mockito is matching Entity object signatures/addresses & they can be different but same data
        lenient().when(accountTransactionRepository.save(inputEntity)).thenReturn(inputEntity);
        log.debug("AccountTransactionServiceTest: @ut_createTransaction - Mock account transaction created / returned");

        AccountTransactionDTO outputDTO = accountTransactionService.createInDB(inputDTO);
        assertEquals(inputDTO, outputDTO);
        log.debug("AccountTransactionServiceTest: @ut_createTransaction - assertEquals check executed");
        log.info("AccountTransactionServiceTest: @ut_createTransaction - executed successfully");
    }
}