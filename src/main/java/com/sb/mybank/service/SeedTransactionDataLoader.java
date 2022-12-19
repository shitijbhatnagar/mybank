package com.sb.mybank.service;

import com.sb.mybank.dto.AccountTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@Profile("dev") //this bean will only be created when the profile being used is "dev"
public class SeedTransactionDataLoader {
    private final AccountTransactionService transactionService;

    public SeedTransactionDataLoader(AccountTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostConstruct
    public void setup()
    {
        log.debug("Creating dev transactions... Started");
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setUserId("someUserId4");
        transactionDTO.setTimestamp(ZonedDateTime.now());
        transactionDTO.setReference("sample 4");
        transactionDTO.setAmount(BigDecimal.valueOf(25));
        transactionService.createInDB(transactionDTO);

        transactionDTO.setUserId("someUserId5");
        transactionDTO.setTimestamp(ZonedDateTime.now());
        transactionDTO.setReference("sample 5");
        transactionDTO.setAmount(BigDecimal.valueOf(26));
        transactionService.createInDB(transactionDTO);

        transactionDTO.setUserId("someUserId6");
        transactionDTO.setTimestamp(ZonedDateTime.now());
        transactionDTO.setReference("sample 6");
        transactionDTO.setAmount(BigDecimal.valueOf(27));
        transactionService.createInDB(transactionDTO);

        log.debug("Creating dev transactions... Completed");
    }
}