package com.example.springLoan.util;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.util.constant.ApplicationConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String localDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(ApplicationConstant.DATE_FORMAT));
    }

    public static String integerToString(Integer integer){
        return integer.toString();
    }

    public static String doubleToString(Double double1){
        return double1.toString();
    }

}
