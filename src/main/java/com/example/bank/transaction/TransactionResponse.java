package com.example.bank.transaction;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionResponse {
    private Long id;
    private String accountNumber;
    private TransactionType type;
    private BigDecimal amount;
    private String counterpartyAccountNumber;
    private Instant createdAt;
    private String memo;

    public TransactionResponse(Long id, String accountNumber, TransactionType type, BigDecimal amount,
                               String counterpartyAccountNumber, Instant createdAt, String memo) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.counterpartyAccountNumber = counterpartyAccountNumber;
        this.createdAt = createdAt;
        this.memo = memo;
    }

    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getCounterpartyAccountNumber() { return counterpartyAccountNumber; }
    public Instant getCreatedAt() { return createdAt; }
    public String getMemo() { return memo; }
}
