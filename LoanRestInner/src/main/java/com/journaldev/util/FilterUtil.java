package com.journaldev.util;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.util.DateTimeUtil;

import java.util.List;

public class FilterUtil {

    public static ProductSetting findProductSettingByValue(List<ProductSetting> productSettings, String value) {
        return productSettings.stream()
                .filter(productSetting ->
                        productSetting.getSettingType().getName().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("findProductSettingByValue: " + value));
    }

    public static ProductTypeSetting findProductTypeSettingByValue(List<ProductTypeSetting> productSettings,
                                                                   String value) {
        return productSettings.stream()
                .filter(productTypeSetting ->
                        productTypeSetting .getSettingType().getName().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("findProductTypeSettingByValue: " + value));
    }

}
