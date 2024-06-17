package com.example.msemilbank.dao.repository;

import com.example.msemilbank.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query(value = "SELECT * FROM accounts WHERE account_number = :accountNumber", nativeQuery = true)
    Optional<AccountEntity> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
