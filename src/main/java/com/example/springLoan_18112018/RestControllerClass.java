package com.example.springLoan_18112018;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.other.ClientDataWrapper;
import com.example.springLoan_18112018.repository.CustomerRepository;
import com.example.springLoan_18112018.repository.ProductRepository;
import com.example.springLoan_18112018.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/***
 *  THIS CLASS IS FOR TESTING ONLY!
 *
 */

@RestController
public class RestControllerClass {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(value = "/products")
    public List<Product> gerProducts() {

        List<Product> products = productRepository.findAll();

        System.out.println(products);

        return productRepository.findAll();
    }

    @RequestMapping(value = "/apply-for-loan")
    public ResponseEntity<Object> applyForLoan(@RequestBody ClientDataWrapper clientDataWrapper){
        Optional<Product> optionalProduct = productService.applyForLoan(clientDataWrapper);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>("Loan is not given based on business rules.", HttpStatus.BAD_REQUEST);
    }





}
