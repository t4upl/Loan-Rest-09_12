package com.example.springLoan.service;

import com.example.springLoan.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findById(Long id);
}
