package com.example.loanrestapi.factory;

public interface AbstractFactory {
  ProductSettingFactory getProductSettingFactory();

  ProductFactory getProductFactory();
}
