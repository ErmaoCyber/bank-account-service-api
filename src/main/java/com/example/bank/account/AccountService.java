package com.example.bank.account;

import com.example.bank.common.BadRequestException;
import com.example.bank.common.ConflictException;
import com.example.bank.common.NotFoundException;
import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRepository;
import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.TransactionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final TransactionService transactionService;

    public AccountService(
            AccountRepository accountRepo,
            CustomerRepository customerRepo,
            TransactionService transactionService
    ) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
        this.transactionService = transactionService;
    }

    public AccountResponse create(CreateAccountRequest req) {
        if (req.getInitialBalance() == null) {
            throw new BadRequestException("Initial balance is required");
        }
        if (req.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Initial balance cannot be negative");
        }

        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Account account = new Account(req.getAccountNumber(), req.getInitialBalance(), customer);

        try {
            Account saved = accountRepo.save(account);

            // Record OPENING transaction for demo UI
            transactionService.recordOpening(saved.getAccountNumber(), saved.getBalance());

            // fetch with customer to avoid lazy issues when creating DTO
            Account withCustomer = accountRepo.findWithCustomerByAccountNumber(saved.getAccountNumber())
                    .orElse(saved);

            return toResponse(withCustomer);
        } catch (DataIntegrityViolationException e) {
            // uk_account_number
            throw new ConflictException("Account number already exists");
        }
    }

    public List<AccountResponse> findAll() {
        // AccountRepository#findAll is annotated with @EntityGraph(customer)
        return accountRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AccountResponse findByAccountNumber(String accountNumber) {
        Account account = accountRepo.findWithCustomerByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return toResponse(account);
    }

    public AccountDetailResponse detail(String accountNumber) {
        Account account = accountRepo.findWithCustomerByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Customer customer = account.getCustomer();
        CustomerResponse customerResponse = new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getAvatarUrl(),
                customer.getAddress(),
                customer.getOccupation()
        );

        return new AccountDetailResponse(account.getAccountNumber(), account.getBalance(), customerResponse);
    }

    private AccountResponse toResponse(Account a) {
        Customer c = a.getCustomer();
        return new AccountResponse(
                a.getId(),
                a.getAccountNumber(),
                a.getBalance(),
                c == null ? null : c.getId(),
                c == null ? null : c.getFullName(),
                c == null ? null : c.getAvatarUrl()
        );
    }
}
