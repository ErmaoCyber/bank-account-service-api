package com.example.bank.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateCustomerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    // Optional wizard profile fields (for demo / portfolio)
    private String avatarUrl;
    private String address;
    private String occupation;

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getAddress() { return address; }
    public String getOccupation() { return occupation; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setAddress(String address) { this.address = address; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
}
