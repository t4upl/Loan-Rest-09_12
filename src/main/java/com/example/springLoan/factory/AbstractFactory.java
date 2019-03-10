package com.example.springLoan.factory;

public interface AbstractFactory {
    ProductSettingFactory getProductSettingFactory();

    ProductFactory getProductFactory();
}
