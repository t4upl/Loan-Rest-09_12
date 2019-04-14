package com.example.loanrestapi.factory;

import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.Setting;

public interface ProductSettingFactory {
  ProductSetting getProductSetting(Long id, String value, Product product, Setting setting);
}
