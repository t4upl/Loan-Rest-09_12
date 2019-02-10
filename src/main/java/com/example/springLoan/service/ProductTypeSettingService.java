package com.example.springLoan.service;

import com.example.springLoan.model.ProductTypeSetting;

import java.time.LocalTime;
import java.util.List;

public interface ProductTypeSettingService {

    Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, String key);

    LocalTime findAndGetAsLocalTime(List<ProductTypeSetting> productTypeSettings, String key);

    Double findAndGetAsDouble(List<ProductTypeSetting> productTypeSettings, String key);
}
