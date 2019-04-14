package com.example.loanrestapi.service;

import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.model.Product;
import java.util.Optional;

public interface ProductService {
  Optional<Product> getLoan(ProductRequestDto productRequestDto);

  Optional<Product> extendLoan(Long productId);
}
