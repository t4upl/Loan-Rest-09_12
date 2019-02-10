package com.example.springLoan.api;

import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Product;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.CustomerRepository;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.example.springLoan.util.constant.ApplicationConstant.SANITY_TEST_ENDPOINT_RESPONSE;


@RestController
public class API {

    @Autowired
    private ProductService productService;


    /**
     * This endpoint is for sanity testing
     */
    @RequestMapping(value = "/sanity-test")
    public ResponseEntity<String> getProduct() {
        return new ResponseEntity<>(SANITY_TEST_ENDPOINT_RESPONSE, HttpStatus.OK);
    }

    @RequestMapping(value = "/apply-for-loan")
    public ResponseEntity<Object> applyForLoan(@RequestBody ClientDataWrapper clientDataWrapper){
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>("Loan is not given based on business rules.", HttpStatus.BAD_REQUEST);
    }





}
