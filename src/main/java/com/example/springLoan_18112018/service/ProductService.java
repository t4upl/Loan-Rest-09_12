package com.example.springLoan_18112018.service;

import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.other.ClientDataWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    @Transactional
    Optional<Product> applyForLoan(ClientDataWrapper clientDataWrapper);

    List<Product> findAll();
}
