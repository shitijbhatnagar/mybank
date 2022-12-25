package com.sb.mybank.util;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
public class MockDataProvider
{
    //Return a list of mocked account transaction DTOs
    public static List<TransactionDTO> getMockTransactionDTOList()
    {
        String uuid1 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("5").toString();
        String uuid2 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("6").toString();
        String uuid3 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("7").toString();

        TransactionDTO transactionDTO1 = new TransactionDTO(), transactionDTO2 = new TransactionDTO(), transactionDTO3 = new TransactionDTO();

        transactionDTO1.setId(uuid1);
        transactionDTO1.setUserId("mockUser1");
        transactionDTO1.setTimestamp(ZonedDateTime.now());
        transactionDTO1.setReference("mock sample 1");
        transactionDTO1.setAmount(BigDecimal.valueOf(80));

        transactionDTO2.setId(uuid2);
        transactionDTO2.setUserId("mockUser2");
        transactionDTO2.setTimestamp(ZonedDateTime.now());
        transactionDTO2.setReference("mock sample 2");
        transactionDTO2.setAmount(BigDecimal.valueOf(81));

        transactionDTO3.setId(uuid3);
        transactionDTO3.setUserId("mockUser3");
        transactionDTO3.setTimestamp(ZonedDateTime.now());
        transactionDTO3.setReference("mock sample 3");
        transactionDTO3.setAmount(BigDecimal.valueOf(82));

        log.debug("MockDataProvider: Transaction mock dto data returned");

        return Arrays.asList(transactionDTO1, transactionDTO2, transactionDTO3);
    }

    //Return a list of mocked account transaction (entity classes)
    public static List<Transaction> getMockTransactionEntityList()
    {
        String uuid1 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("1").toString();
        String uuid2 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("2").toString();
        String uuid3 = new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("3").toString();

        Transaction transaction1 = new Transaction(), transaction2 = new Transaction(), transaction3 = new Transaction();
        transaction1.setId(uuid1);
        transaction1.setUserId("mockUser1");
        transaction1.setTimestamp(ZonedDateTime.now());
        transaction1.setReference("mock sample 1");
        transaction1.setAmount(BigDecimal.valueOf(80));

        transaction2.setId(uuid2);
        transaction2.setUserId("mockUser2");
        transaction2.setTimestamp(ZonedDateTime.now());
        transaction2.setReference("mock sample 2");
        transaction2.setAmount(BigDecimal.valueOf(81));

        transaction3.setId(uuid3);
        transaction3.setUserId("mockUser3");
        transaction3.setTimestamp(ZonedDateTime.now());
        transaction3.setReference("mock sample 3");
        transaction3.setAmount(BigDecimal.valueOf(82));

        log.debug("MockDataProvider: Transaction mock entity data returned");

        return Arrays.asList(transaction1, transaction2, transaction3);
    }

    public static TransactionDTO getMockTransactionDTO()
    {
        //Create a mock transaction
        TransactionDTO inputDTO = new TransactionDTO();
        inputDTO.setId(new StringBuilder().append(APIEndPointsAndConstants.const_uuid.substring(0,APIEndPointsAndConstants.const_uuid.length()-1)).append("4").toString());
        inputDTO.setUserId("mockUser20");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 20");
        inputDTO.setAmount(BigDecimal.valueOf(25));

        return inputDTO;
    }

    //This method returns DTO that can be used in an actual create/insert operation
    public static TransactionDTO getTestTransactionDTO()
    {
        TransactionDTO inputDTO = new TransactionDTO();
        inputDTO.setUserId("mockUser001");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 1");
        inputDTO.setAmount(BigDecimal.valueOf(1));

        return inputDTO;
    }
    public static TransactionDTO convertEntityToDTO(Transaction transaction)
    {
        TransactionDTO transactionDTO = new TransactionDTO();
        //if transaction.id is not null, only then set the id to a value in the dto
        Optional<String> optional = Optional.ofNullable(transaction.getId());
        if(optional.isPresent())
        {
            transactionDTO.setId(transaction.getId());
        }
        transactionDTO.setReference(transaction.getReference());
        transactionDTO.setUserId(transaction.getUserId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setTimestamp(transaction.getTimestamp().toLocalDateTime().atZone(ZoneId.systemDefault()));
        log.debug("MockDataProvider: convertEntityToDTO() successfully called");
        return transactionDTO;
    }

    public static Transaction convertDTOToEntity(TransactionDTO transactionDTO)
    {
        Transaction transaction = new Transaction();
        //if transaction.id is not null, only then set the id to a value in the dto
        Optional<String> optional = Optional.ofNullable(transactionDTO.getId());
        if(optional.isPresent())
        {
            transaction.setId(transactionDTO.getId());
        }
        transaction.setTimestamp(transactionDTO.getTimestamp());
        transaction.setUserId(transactionDTO.getUserId());
        transaction.setReference(transactionDTO.getReference());
        transaction.setAmount(transactionDTO.getAmount());
        log.debug("MockDataProvider: convertDTOToEntity() successfully called");
        return transaction;
    }

    public static String getMockTransactionJSON()
    {
        return "[{\\\"id\\\":\\\"dc82a225-bfff-4bb7-b13b-cdba279a6058\\\",\\\"userId\\\":\\\"mockUser58\\\",\\\"timestamp\\\":\\\"2022-12-18T21:49+0530\\\",\\\"reference\\\":\\\"mock sample 58\\\",\\\"amount\\\":58}]";
    }

    public static String getMockCreateTransactionRequestJSON()
    {
        return "{\"userId\":\"mockUser58\",\"timestamp\":\"2022-12-18T21:49+0530\",\"reference\":\"mock sample 58\",\"amount\":58}";
    }

    public static String getMockCreateTransactionResponseJSON()
    {
        return "{\"id\":\"dc82a225-bfff-4bb7-b13b-cdba279a6058\",\"userId\":\"mockUser58\",\"timestamp\":\"2022-12-18T21:49+0530\",\"reference\":\"mock sample 58\",\"amount\":58}";
    }
}