package com.example.springLoan.factory;

import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.Setting;

public interface ProductSettingFactory {
    ProductSetting getProductSetting(Integer id, String value, Product product, Setting setting);
}
