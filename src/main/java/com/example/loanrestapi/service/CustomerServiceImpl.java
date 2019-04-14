package com.example.loanrestapi.service;

import com.example.loanrestapi.model.Customer;
import com.example.loanrestapi.repository.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Override
  public Optional<Customer> findById(Long id) {
    return customerRepository.findById(id);
  }
}
