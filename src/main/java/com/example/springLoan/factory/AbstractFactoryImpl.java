package com.example.springLoan.factory;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbstractFactoryImpl implements AbstractFactory {

    ProductSettingFactory productSettingFactory;

    ProductFactory productFactory;

    AbstractFactoryImpl (ProductSettingFactory productSettingFactory, ProductFactory productFactory){
        this.productSettingFactory = productSettingFactory;
        this.productFactory = productFactory;
    }

    @Override
    public ProductSettingFactory getProductSettingFactory() {
        return productSettingFactory;
    }

    @Override
    public ProductFactory getProductFactory() { return productFactory; }
}
