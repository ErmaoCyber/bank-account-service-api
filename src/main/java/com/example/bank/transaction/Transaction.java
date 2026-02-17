package com.example.bank.transaction;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "idx_tx_account_number", columnList = "accountNumber"),
        @Index(name = "idx_tx_created_at", columnList = "createdAt")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    // 例如转账对手方账号；开户则为 null
    private String counterpartyAccountNumber;

    // Optional description for UI demo (e.g., "Opening deposit", "Payment for butterbeer")
    @Column(length = 200)
    private String memo;

    @Column(nullable = false)
    private Instant createdAt;

    protected Transaction() {}

    public Transaction(String accountNumber, TransactionType type, BigDecimal amount, String counterpartyAccountNumber) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.counterpartyAccountNumber = counterpartyAccountNumber;
    }

    public Transaction(String accountNumber, TransactionType type, BigDecimal amount, String counterpartyAccountNumber, String memo) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.counterpartyAccountNumber = counterpartyAccountNumber;
        this.memo = memo;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getCounterpartyAccountNumber() { return counterpartyAccountNumber; }
    public String getMemo() { return memo; }
    public Instant getCreatedAt() { return createdAt; }
}
