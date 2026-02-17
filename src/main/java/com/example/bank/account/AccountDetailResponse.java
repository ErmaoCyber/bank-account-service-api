package com.example.bank.account;

import com.example.bank.customer.CustomerResponse;

import java.math.BigDecimal;

public record AccountDetailResponse(
        String accountNumber,
        BigDecimal balance,
        CustomerResponse customer
) {}
