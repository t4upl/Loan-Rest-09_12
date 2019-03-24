package com.example.springLoan.factory;

import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProductFactoryImpl implements ProductFactory {

    @Override
    public Product getProduct(Long id, Customer customer, ProductType productType,
                              Set<ProductSetting> productSettings){
        return new Product(id, customer, productType, productSettings);
    }

}
