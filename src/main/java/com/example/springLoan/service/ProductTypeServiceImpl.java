package com.example.springLoan.service;

import com.example.springLoan.model.ProductType;
import com.example.springLoan.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public Optional<ProductType> findById(Integer id){
        return productTypeRepository.findById(id);
    }
}
