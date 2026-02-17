package com.example.bank.transfer;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. Transfers")
@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public TransferResult transfer(@Valid @RequestBody TransferRequest req) {
        return service.transfer(req);
    }
}
