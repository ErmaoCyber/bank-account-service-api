package com.example.bank.customer;

public class CustomerResponse {
    private Long id;
    private String fullName;
    private String email;

    // wizard profile fields
    private String avatarUrl;
    private String address;
    private String occupation;

    public CustomerResponse(Long id, String fullName, String email, String avatarUrl, String address, String occupation) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.occupation = occupation;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getAddress() { return address; }
    public String getOccupation() { return occupation; }
}
