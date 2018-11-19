package com.example.springLoan_18112018;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
