package com.example.springLoan.util;

import com.example.springLoan.model.ProductTypeSetting;

import java.util.List;

public class FilterUtil {

    public static ProductTypeSetting findProductTypeSettingByValue(List<ProductTypeSetting> productSettings,
                                                                   String value) {
        return productSettings
                .stream()
                .filter(productTypeSetting ->
                        productTypeSetting.getSetting().getName().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("findProductTypeSettingByValue: " + value));
    }
}
