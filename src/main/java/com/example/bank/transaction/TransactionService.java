package com.example.bank.transaction;

import com.example.bank.account.AccountRepository;
import com.example.bank.common.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository txRepo, AccountRepository accountRepo) {
        this.txRepo = txRepo;
        this.accountRepo = accountRepo;
    }

    // ✅ 用于 AccountService：开户流水
    public void recordOpening(String accountNumber, BigDecimal initialBalance) {
        txRepo.save(new Transaction(
                accountNumber,
                TransactionType.OPENING,
                initialBalance,
                null,
                "Opening deposit"
        ));
    }

    // ✅ 用于 TransferService：转账流水（两条）
    public void recordTransfer(String fromAccount, String toAccount, BigDecimal amount) {
        txRepo.save(new Transaction(
                fromAccount,
                TransactionType.TRANSFER_OUT,
                amount,
                toAccount,
                "Transfer to " + toAccount
        ));
        txRepo.save(new Transaction(
                toAccount,
                TransactionType.TRANSFER_IN,
                amount,
                fromAccount,
                "Transfer from " + fromAccount
        ));
    }

    public Page<TransactionResponse> listByAccountNumber(String accountNumber, Pageable pageable) {
        // 先校验账号存在：前端体验更好（404，而不是空列表误导）
        accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return txRepo.findByAccountNumberOrderByCreatedAtDesc(accountNumber, pageable)
                .map(this::toResponse);
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getAccountNumber(),
                t.getType(),
                t.getAmount(),
                t.getCounterpartyAccountNumber(),
                t.getCreatedAt(),
                t.getMemo()
        );
    }
}
