package com.example.bank.transaction;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4. Transactions")
@RestController
@RequestMapping("/api/v1/transactions")
@Validated
public class TransactionQueryController {

    private final TransactionService service;

    public TransactionQueryController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TransactionResponse> listByAccount(
            @Parameter(
                    description = "Account number to query transactions for",
                    example = "A-1001",
                    required = true
            )
            @RequestParam @NotBlank String accountNumber,

            @ParameterObject Pageable pageable
    ) {
        return service.listByAccountNumber(accountNumber, pageable);
    }
}
