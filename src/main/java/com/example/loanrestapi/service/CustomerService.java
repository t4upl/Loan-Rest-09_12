package com.example.loanrestapi.service;

import com.example.loanrestapi.model.Customer;

import java.util.Optional;

public interface CustomerService {
  Optional<Customer> findById(Long id);
}
