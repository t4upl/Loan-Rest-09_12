package com.example.springLoan.service;

import com.example.springLoan.model.ProductType;

import java.util.Optional;

public interface ProductTypeService {
    Optional<ProductType> findById(Integer id);
}
