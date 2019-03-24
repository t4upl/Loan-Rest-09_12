package com.example.springLoan.factory;

import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.Setting;
import org.springframework.stereotype.Component;

@Component
public class ProductSettingFactoryImpl implements ProductSettingFactory {
    @Override
    public ProductSetting getProductSetting(Long id, String value, Product product, Setting setting) {
        return new ProductSetting(id, value, product, setting);
    }
}
