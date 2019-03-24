package com.example.springLoan.service;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.model.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> getLoan(ProductRequestDTO productRequestDTO);
    Optional<Product> extendLoan(Long productId);
}
