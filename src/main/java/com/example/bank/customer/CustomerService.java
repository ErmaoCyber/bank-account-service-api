package com.example.bank.customer;

import com.example.bank.common.ConflictException;
import com.example.bank.common.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public CustomerResponse create(CreateCustomerRequest req) {

        // nicer error before hitting DB unique constraint
        if (repo.existsByEmail(req.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        Customer customer = new Customer(
                req.getFullName(),
                req.getEmail(),
                req.getAvatarUrl(),
                req.getAddress(),
                req.getOccupation()
        );

        try {
            Customer saved = repo.save(customer);
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            // race-condition fallback (email unique constraint)
            throw new ConflictException("Email already exists");
        }
    }

    public List<CustomerResponse> findAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerResponse findById(Long id) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return toResponse(customer);
    }

    public CustomerResponse update(Long id, UpdateCustomerRequest req) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        // If email changed, enforce uniqueness
        String newEmail = req.getEmail();
        if (newEmail != null && !newEmail.equalsIgnoreCase(customer.getEmail())) {
            if (repo.existsByEmail(newEmail)) {
                throw new ConflictException("Email already exists");
            }
        }

        customer.setFullName(req.getFullName());
        customer.setEmail(req.getEmail());
        customer.setAvatarUrl(req.getAvatarUrl());
        customer.setAddress(req.getAddress());
        customer.setOccupation(req.getOccupation());

        try {
            Customer saved = repo.save(customer);
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Email already exists");
        }
    }

    public DeleteResponse delete(Long id) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        repo.delete(customer);
        return new DeleteResponse("Customer deleted successfully", id);
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFullName(),
                c.getEmail(),
                c.getAvatarUrl(),
                c.getAddress(),
                c.getOccupation()
        );
    }
}
