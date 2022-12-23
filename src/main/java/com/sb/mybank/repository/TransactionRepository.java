package com.sb.mybank.repository;

import com.sb.mybank.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, String>
{
    //Standard implementation available in CrudRepository interface
}
