package com.example.bank.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @EntityGraph(attributePaths = {"customer"})
    List<Account> findAll();

    @EntityGraph(attributePaths = {"customer"})
    Optional<Account> findWithCustomerByAccountNumber(String accountNumber);
}
