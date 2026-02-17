package com.example.bank.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Accounts")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return service.findAll();
    }

    // Account detail view for the frontend demo (includes wizard profile)
    @GetMapping("/{accountNumber}/detail")
    public AccountDetailResponse detail(@PathVariable String accountNumber) {
        return service.detail(accountNumber);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse findByAccountNumber(@PathVariable String accountNumber) {
        return service.findByAccountNumber(accountNumber);
    }
}
