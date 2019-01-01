package com.example.springLoan.service;

import com.example.springLoan.model.Product;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DecisionSystem decisionSystem;

    @Override
    public Optional<Product> applyForLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return Optional.empty();
        }

        return Optional.empty();
    }
}
