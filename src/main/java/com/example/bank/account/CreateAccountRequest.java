package com.example.bank.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateAccountRequest {

    @NotNull
    private Long customerId;

    @NotBlank
    private String accountNumber;

    @NotNull
    private BigDecimal initialBalance;

    public Long getCustomerId() { return customerId; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getInitialBalance() { return initialBalance; }

    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}
