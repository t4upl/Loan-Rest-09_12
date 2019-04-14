package com.example.loanrestapi.factory;

import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.Setting;
import org.springframework.stereotype.Component;

@Component
public class ProductSettingFactoryImpl implements ProductSettingFactory {
    @Override
    public ProductSetting getProductSetting(Long id, String value, Product product, Setting setting) {
        return new ProductSetting(id, value, product, setting);
    }
}
