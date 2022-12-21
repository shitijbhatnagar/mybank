package com.sb.mybank.util;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.model.AccountTransaction;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
public class MockDataProvider
{
    //Return a list of mocked account transaction DTOs
    public static List<AccountTransactionDTO> getMockTransactionDTOList()
    {
        AccountTransactionDTO transactionDTO1 = new AccountTransactionDTO(), transactionDTO2 = new AccountTransactionDTO(), transactionDTO3 = new AccountTransactionDTO();
        transactionDTO1.setUserId("mockUser1");
        transactionDTO1.setTimestamp(ZonedDateTime.now());
        transactionDTO1.setReference("mock sample 1");
        transactionDTO1.setAmount(BigDecimal.valueOf(80));

        transactionDTO2.setUserId("mockUser2");
        transactionDTO2.setTimestamp(ZonedDateTime.now());
        transactionDTO2.setReference("mock sample 2");
        transactionDTO2.setAmount(BigDecimal.valueOf(81));

        transactionDTO3.setUserId("mockUser3");
        transactionDTO3.setTimestamp(ZonedDateTime.now());
        transactionDTO3.setReference("mock sample 3");
        transactionDTO3.setAmount(BigDecimal.valueOf(82));

        log.debug("MockDataProvider: Transaction mock dto data setup");

        return Arrays.asList(transactionDTO1, transactionDTO2, transactionDTO3);
    }

    //Return a list of mocked account transaction (entity classes)
    public static List<AccountTransaction> getMockTransactionEntityList()
    {
        AccountTransaction transaction1 = new AccountTransaction(), transaction2 = new AccountTransaction(), transaction3 = new AccountTransaction();
        transaction1.setUserId("mockUser1");
        transaction1.setTimestamp(ZonedDateTime.now());
        transaction1.setReference("mock sample 1");
        transaction1.setAmount(BigDecimal.valueOf(80));

        transaction2.setUserId("mockUser2");
        transaction2.setTimestamp(ZonedDateTime.now());
        transaction2.setReference("mock sample 2");
        transaction2.setAmount(BigDecimal.valueOf(81));

        transaction3.setUserId("mockUser3");
        transaction3.setTimestamp(ZonedDateTime.now());
        transaction3.setReference("mock sample 3");
        transaction3.setAmount(BigDecimal.valueOf(82));

        log.debug("MockDataProvider: Transaction mock entity data setup");

        return Arrays.asList(transaction1, transaction2, transaction3);
    }

    public static AccountTransactionDTO getMockTransactionDTO()
    {
        //Create a mock transaction
        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
        inputDTO.setUserId("mockUser20");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 20");
        inputDTO.setAmount(BigDecimal.valueOf(25));

        return inputDTO;
    }

    public static AccountTransactionDTO convertEntityToDTO(AccountTransaction transaction)
    {
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setReference(transaction.getReference());
        transactionDTO.setUserId(transaction.getUserId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setTimestamp(transaction.getTimestamp().toLocalDateTime().atZone(ZoneId.systemDefault()));
        log.debug("MockDataProvider: convertEntityToDTO() successfully called");
        return transactionDTO;
    }

    public static AccountTransaction convertDTOToEntity(AccountTransactionDTO transactionDTO)
    {
        AccountTransaction transaction = new AccountTransaction();
        transaction.setTimestamp(transactionDTO.getTimestamp());
        transaction.setUserId(transactionDTO.getUserId());
        transaction.setReference(transactionDTO.getReference());
        transaction.setAmount(transactionDTO.getAmount());
        log.debug("MockDataProvider: convertDTOToEntity() successfully called");
        return transaction;
    }

    public static String getMockTransactionJSON()
    {
        return "[{\\\"userId\\\":\\\"mockUser1\\\",\\\"timestamp\\\":\\\"2022-12-18T21:49+0530\\\",\\\"reference\\\":\\\"mock sample 1\\\",\\\"amount\\\":80}]";
    }

    public static String getMockCreateTransactionJSON()
    {
        return "{\"userId\":\"mockUser1\",\"timestamp\":\"2022-12-18T21:49+0530\",\"reference\":\"mock sample 1\",\"amount\":80}";
    }
}
