package com.example.loanrestapi.service;

import com.example.loanrestapi.model.ProductType;
import java.util.Optional;

public interface ProductTypeService {

  Optional<ProductType> findById(Long id);
}
