package com.sb.mybank.util;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.AccountTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
public class MockDataProvider
{
    //Return a list of mocked account transactions
    public static List<AccountTransactionDTO> getMockTransactionList()
    {
        String uuid1 = (new StringBuilder())
                .append(APIEndPointsAndConstants.const_uuidBaseValue.substring(0, APIEndPointsAndConstants.const_uuidBaseValue.length() - 1))
                .append("1").toString();
        AccountTransactionDTO transactionDTO1 = new AccountTransactionDTO(), transactionDTO2 = new AccountTransactionDTO(), transactionDTO3 = new AccountTransactionDTO();
        transactionDTO1.setId(uuid1);
        transactionDTO1.setUserId("mockUser1");
        transactionDTO1.setTimestamp(ZonedDateTime.now());
        transactionDTO1.setReference("mock sample 1");
        transactionDTO1.setAmount(BigDecimal.valueOf(80));

        String uuid2 = (new StringBuilder())
                .append(APIEndPointsAndConstants.const_uuidBaseValue.substring(0, APIEndPointsAndConstants.const_uuidBaseValue.length() - 1))
                .append("2").toString();
        transactionDTO2.setId(uuid2);
        transactionDTO2.setUserId("mockUser2");
        transactionDTO2.setTimestamp(ZonedDateTime.now());
        transactionDTO2.setReference("mock sample 2");
        transactionDTO2.setAmount(BigDecimal.valueOf(81));

        String uuid3 = (new StringBuilder())
                .append(APIEndPointsAndConstants.const_uuidBaseValue.substring(0, APIEndPointsAndConstants.const_uuidBaseValue.length() - 1))
                .append("2").toString();
        transactionDTO3.setId(uuid3);
        transactionDTO3.setUserId("mockUser3");
        transactionDTO3.setTimestamp(ZonedDateTime.now());
        transactionDTO3.setReference("mock sample 3");
        transactionDTO3.setAmount(BigDecimal.valueOf(82));

        log.debug("MockDataProvider: Transaction mock data setup");

        return Arrays.asList(transactionDTO1, transactionDTO2, transactionDTO3);
    }

    public static AccountTransactionDTO getMockTransaction()
    {
        //Create a mock transaction
        AccountTransactionDTO inputDTO = new AccountTransactionDTO();
        inputDTO.setId(APIEndPointsAndConstants.const_uuidBaseValue);
        inputDTO.setUserId("mockUser20");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 20");
        inputDTO.setAmount(BigDecimal.valueOf(25));

        return inputDTO;
    }

    public static String getMockTransactionJSON()
    {
        return "[{\\\"id\":\\\"dc82a225-bfff-4bb7-b13b-cdba279a60b7\\\",\\\"userId\\\":\\\"mockUser1\\\",\\\"timestamp\\\":\\\"2022-12-18T21:49+0530\\\",\\\"reference\\\":\\\"mock sample 1\\\",\\\"amount\\\":80}]";
    }

    public static String getMockCreateTransactionJSON()
    {
        return "{\"id\":\"dc82a225-bfff-4bb7-b13b-cdba279a60b7\",\"userId\":\"mockUser1\",\"timestamp\":\"2022-12-18T21:49+0530\",\"reference\":\"mock sample 1\",\"amount\":80}";
    }
}
