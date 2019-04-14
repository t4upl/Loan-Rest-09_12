package com.example.loanrestapi.factory;

import com.example.loanrestapi.model.Customer;
import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductType;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ProductFactoryImpl implements ProductFactory {

  @Override
  public Product getProduct(Long id, Customer customer, ProductType productType,
      Set<ProductSetting> productSettings) {
    return new Product(id, customer, productType, productSettings);
  }

}
