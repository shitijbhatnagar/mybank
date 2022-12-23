package com.sb.mybank.repository;

import com.sb.mybank.model.AccountTransaction;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

@Component
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, String>
{
    //Standard implementation available in CrudRepository interface
}
