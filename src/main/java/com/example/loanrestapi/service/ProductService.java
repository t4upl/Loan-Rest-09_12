package com.example.loanrestapi.service;

import com.example.loanrestapi.dto.ProductRequestDTO;
import com.example.loanrestapi.model.Product;
import java.util.Optional;

public interface ProductService {
  Optional<Product> getLoan(ProductRequestDTO productRequestDTO);

  Optional<Product> extendLoan(Long productId);
}
