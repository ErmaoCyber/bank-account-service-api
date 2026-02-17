package com.example.bank.config;

import com.example.bank.account.AccountService;
import com.example.bank.account.CreateAccountRequest;
import com.example.bank.customer.CreateCustomerRequest;
import com.example.bank.customer.CustomerResponse;
import com.example.bank.customer.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Seeds demo data (Harry Potter wizards) when the database is empty.
 *
 * This is intentionally simple for portfolio demo purposes.
 */
@Component
public class DemoDataInitializer implements CommandLineRunner {

    private final CustomerService customerService;
    private final AccountService accountService;

    public DemoDataInitializer(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) {
        // If you already have customers, do not seed.
        // (We use the public API layer here to ensure DTO mapping + opening transactions happen.)
        if (!customerService.findAll().isEmpty()) {
            return;
        }

        CustomerResponse harry = createWizard(
                "Harry Potter",
                "harry@hogwarts.magic",
                "avatars/harry.svg",
                "4 Privet Drive, Little Whinging",
                "Auror"
        );
        CustomerResponse hermione = createWizard(
                "Hermione Granger",
                "hermione@hogwarts.magic",
                "avatars/hermione.svg",
                "Hampstead Garden Suburb, London",
                "Ministry of Magic"
        );
        CustomerResponse ron = createWizard(
                "Ron Weasley",
                "ron@hogwarts.magic",
                "avatars/ron.svg",
                "The Burrow, Ottery St Catchpole",
                "Auror"
        );
        CustomerResponse draco = createWizard(
                "Draco Malfoy",
                "draco@hogwarts.magic",
                "avatars/draco.svg",
                "Malfoy Manor, Wiltshire",
                "Wizarding Investor"
        );

        createAccount("GR-0001", new BigDecimal("5200.00"), harry.getId());
        createAccount("GR-0002", new BigDecimal("8800.00"), hermione.getId());
        createAccount("GR-0003", new BigDecimal("1300.00"), ron.getId());
        createAccount("GR-0004", new BigDecimal("12000.00"), draco.getId());
    }

    private CustomerResponse createWizard(String name, String email, String avatarUrl, String address, String occupation) {
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setFullName(name);
        req.setEmail(email);
        req.setAvatarUrl(avatarUrl);
        req.setAddress(address);
        req.setOccupation(occupation);
        return customerService.create(req);
    }

    private void createAccount(String accountNumber, BigDecimal initialBalance, Long customerId) {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(accountNumber);
        req.setInitialBalance(initialBalance);
        req.setCustomerId(customerId);
        accountService.create(req);
    }
}
