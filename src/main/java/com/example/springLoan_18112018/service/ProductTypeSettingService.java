package com.example.springLoan_18112018.service;

import com.example.springLoan_18112018.model.ProductTypeSetting;

import java.util.List;

public interface ProductTypeSettingService {

    Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, String key);
}
