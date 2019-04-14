package com.example.loanrestapi.factory;

import com.example.loanrestapi.model.Customer;
import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductType;

import java.util.Set;

public interface ProductFactory {
  Product getProduct(Long id, Customer customer, ProductType productType,
      Set<ProductSetting> productSettings);
}
