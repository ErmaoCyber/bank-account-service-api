package com.example.bank.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    // Wizard profile fields (for portfolio demo)
    @Column(length = 400)
    private String avatarUrl;

    @Column(length = 200)
    private String address;

    @Column(length = 120)
    private String occupation;

    protected Customer() {}

    public Customer(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Customer(String fullName, String email, String avatarUrl, String address, String occupation) {
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

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setEmail(String email) { this.email = email; }

    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public void setAddress(String address) { this.address = address; }

    public void setOccupation(String occupation) { this.occupation = occupation; }
}
