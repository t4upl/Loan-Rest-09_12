package com.example.springLoan_18112018.Repository;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.model.ProductType;
import com.example.springLoan_18112018.repository.CustomerRepository;
import com.example.springLoan_18112018.repository.ProductRepository;
import com.example.springLoan_18112018.repository.ProductTypeRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(value = "/product-types")
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    @RequestMapping(value = "/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }


}
