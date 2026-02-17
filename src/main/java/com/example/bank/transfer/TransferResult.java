package com.example.bank.transfer;

import java.math.BigDecimal;

public class TransferResult {
    private final String fromAccountNumber;
    private final String toAccountNumber;
    private final BigDecimal amount;
    private final String status;

    public TransferResult(String from, String to, BigDecimal amount, String status) {
        this.fromAccountNumber = from;
        this.toAccountNumber = to;
        this.amount = amount;
        this.status = status;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
