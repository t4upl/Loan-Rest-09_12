package com.example.springLoan.service;

import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductType;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.CustomerRepository;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    DecisionSystem decisionSystem;

    @Override
    public Optional<Product> getLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return Optional.of(insertProductToDatabase(clientDataWrapper));
        }

        return Optional.empty();
    }

    private Set<ProductSetting> getPtodductSettings(){
        return new HashSet<>();
    }

    private Product insertProductToDatabase(ClientDataWrapper clientDataWrapper){
        Customer customer = customerRepository.findById(clientDataWrapper.getCustomerId())
                .orElseThrow(() -> new RuntimeException(""));

        ProductType productType = productTypeRepository.findById(clientDataWrapper.getProductTypeId())
                .orElseThrow(() -> new RuntimeException(""));

        Product product = new Product(-1, customer, productType, getPtodductSettings());
        return productRepository.save(product);

    }
}
