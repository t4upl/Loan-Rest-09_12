package com.example.springLoan.util;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.util.constant.ApplicationConstant;
import com.example.springLoan.util.constant.EntityUtil;

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

    public static String convertJavaToString(Object javaObject, String dataTypeName){
        switch (dataTypeName) {
            case (EntityUtil.DataType.LOCAL_DATE_TIME):
                return localDateTimeToString((LocalDateTime)javaObject);
            case (EntityUtil.DataType.INTEGER):
                return integerToString((Integer)javaObject);
            case (EntityUtil.DataType.DOUBLE):
                return doubleToString((Double) javaObject);

        }
        throw new RuntimeException(String.format("convertJavaToString Error: %s DataType unknown", dataTypeName));
    }

    private static String localDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(ApplicationConstant.DATE_FORMAT));
    }

    private static String integerToString(Integer integer){
        return integer.toString();
    }

    private static String doubleToString(Double double1){
        return double1.toString();
    }


}
