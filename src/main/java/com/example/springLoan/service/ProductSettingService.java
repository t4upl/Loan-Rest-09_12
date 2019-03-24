package com.example.springLoan.service;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.model.ProductSetting;

import java.util.List;
import java.util.Set;

public interface ProductSettingService {
    List<ProductSetting> saveAll(Iterable<ProductSetting> productSettings);

    Set<ProductSetting> getProductSettings(ProductRequestDTO productRequestDTO);


}
