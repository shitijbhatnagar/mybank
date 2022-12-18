package com.sb.mybank.util;

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
        AccountTransactionDTO transactionDTO1 = new AccountTransactionDTO(), transactionDTO2 = new AccountTransactionDTO(), transactionDTO3 = new AccountTransactionDTO();
        transactionDTO1.setId(UUID.randomUUID().toString());
        transactionDTO1.setUserId("mockUser1");
        transactionDTO1.setTimestamp(ZonedDateTime.now());
        transactionDTO1.setReference("mock sample 1");
        transactionDTO1.setAmount(BigDecimal.valueOf(80));

        transactionDTO2.setId(UUID.randomUUID().toString());
        transactionDTO2.setUserId("mockUser2");
        transactionDTO2.setTimestamp(ZonedDateTime.now());
        transactionDTO2.setReference("mock sample 2");
        transactionDTO2.setAmount(BigDecimal.valueOf(81));

        transactionDTO3.setId(UUID.randomUUID().toString());
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
        inputDTO.setId(UUID.randomUUID().toString());
        inputDTO.setUserId("mockUser20");
        inputDTO.setTimestamp(ZonedDateTime.now());
        inputDTO.setReference("mock sample 20");
        inputDTO.setAmount(BigDecimal.valueOf(25));

        return inputDTO;
    }
}
