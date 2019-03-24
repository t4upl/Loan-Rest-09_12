package com.example.springLoan.service;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.model.ProductSetting;

import java.util.Set;

public interface ProductSettingService {
    Set<ProductSetting> getProductSettings(ProductRequestDTO productRequestDTO);
    Set<ProductSetting> addExtensionTermToDueDate(Set<ProductSetting> productSettingSet);

}
