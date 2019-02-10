package com.example.springLoan.service;

import com.example.springLoan.model.Customer;
import com.example.springLoan.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }
}
