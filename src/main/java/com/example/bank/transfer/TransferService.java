package com.example.bank.transfer;

import com.example.bank.account.Account;
import com.example.bank.account.AccountRepository;
import com.example.bank.common.BadRequestException;
import com.example.bank.common.ConflictException;
import com.example.bank.common.NotFoundException;
import com.example.bank.transaction.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final AccountRepository accountRepo;
    private final TransactionService transactionService;

    public TransferService(AccountRepository accountRepo, TransactionService transactionService) {
        this.accountRepo = accountRepo;
        this.transactionService = transactionService;
    }

    @Transactional
    public TransferResult transfer(TransferRequest req) {
        if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be positive");
        }

        Account from = accountRepo.findByAccountNumber(req.getFromAccountNumber())
                .orElseThrow(() -> new NotFoundException("From account not found"));

        Account to = accountRepo.findByAccountNumber(req.getToAccountNumber())
                .orElseThrow(() -> new NotFoundException("To account not found"));

        if (from.getBalance().compareTo(req.getAmount()) < 0) {
            throw new ConflictException("Insufficient funds");
        }

        // 1) debit
        from.setBalance(from.getBalance().subtract(req.getAmount()));
        accountRepo.save(from);

        // Demo：failure after debit（此时余额、流水都会回滚）
        if (req.isFailAfterDebit()) {
            throw new RuntimeException("Simulated failure after debit (rollback demo)");
        }

        // 2) credit
        to.setBalance(to.getBalance().add(req.getAmount()));
        accountRepo.save(to);

        // ✅ 3) record transactions (2 rows)
        transactionService.recordTransfer(from.getAccountNumber(), to.getAccountNumber(), req.getAmount());

        return new TransferResult(req.getFromAccountNumber(), req.getToAccountNumber(), req.getAmount(), "SUCCESS");
    }
}
