package com.example.springLoan.service;

import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.Setting;

import java.util.List;
import java.util.Set;

public interface ProductSettingService {

    ProductSetting getProductSetting(Integer id, String value, Product product, Setting setting);

    List<ProductSetting> saveAll(Iterable<ProductSetting> productSettings);


}
