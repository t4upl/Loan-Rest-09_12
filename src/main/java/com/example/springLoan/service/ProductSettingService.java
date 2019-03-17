package com.example.springLoan.service;

import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.Setting;
import com.example.springLoan.other.ClientDataWrapper;

import java.util.List;
import java.util.Set;

public interface ProductSettingService {
    List<ProductSetting> saveAll(Iterable<ProductSetting> productSettings);

    Set<ProductSetting> getProductSettings(ClientDataWrapper clientDataWrapper);


}
