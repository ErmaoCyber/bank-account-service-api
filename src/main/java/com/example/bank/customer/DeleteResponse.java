package com.example.bank.customer;

public class DeleteResponse {
    private final String message;
    private final Long id;

    public DeleteResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() { return message; }
    public Long getId() { return id; }
}
