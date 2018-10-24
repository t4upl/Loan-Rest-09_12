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

    public static ClientDataWrapper getClientDataWrapperForLoan() {
        return ClientDataWrapper.builder()
                                .amount(2000)
                                .applicationDate(DateTimeUtil.getLocalDateTime("2018-10-23 00:00:00"))
                                .customerId(1)
                                .productTypeId(1)
                                .term(10)
                                .build();
    }

}
