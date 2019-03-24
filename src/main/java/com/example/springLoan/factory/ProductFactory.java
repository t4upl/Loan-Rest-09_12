package com.example.springLoan.factory;

import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductType;

import java.util.Set;

public interface ProductFactory {
    Product getProduct(Long id, Customer customer, ProductType productType, Set<ProductSetting> productSettings);
}
