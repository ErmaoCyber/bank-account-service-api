package com.example.bank.account;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String accountNumber,
        BigDecimal balance,
        Long customerId,
        String customerFullName,
        String customerAvatarUrl
) {}
