package com.example.loanrestapi.service;

import com.example.loanrestapi.model.ProductType;
import com.example.loanrestapi.repository.ProductTypeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

  @Autowired
  ProductTypeRepository productTypeRepository;

  @Override
  public Optional<ProductType> findById(Long id) {
    return productTypeRepository.findById(id);
  }
}
