package com.example.springLoan.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbstractFactoryImpl implements AbstractFactory {

    @Autowired
    ProductSettingFactory productSettingFactory;

    @Override
    public ProductSettingFactory getProductSettingFactory() {
        return productSettingFactory;
    }
}
