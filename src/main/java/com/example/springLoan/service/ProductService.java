package com.example.springLoan.service;

import com.example.springLoan.model.Product;
import com.example.springLoan.other.ClientDataWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getLoan(ClientDataWrapper clientDataWrapper);
}
