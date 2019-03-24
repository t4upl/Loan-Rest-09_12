package com.example.springLoan.util;

import com.example.springLoan.util.constant.ApplicationConstant;
import com.example.springLoan.util.constant.EntityUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilterUtil {

    public static String convertJavaToString(Object javaObject, String dataTypeName){
        switch (dataTypeName) {
            case (EntityUtil.DataType.LOCAL_DATE_TIME):
                return localDateTimeToString((LocalDateTime)javaObject);
            case (EntityUtil.DataType.INTEGER):
                return integerToString((Integer)javaObject);
            case (EntityUtil.DataType.DOUBLE):
                return doubleToString((Double) javaObject);
        }
        throw new RuntimeException(String.format("convertJavaToString Exception: %s DataType unknown", dataTypeName));
    }

    public static Object convertStringToJava(String dataTypeName, String objectString){
        switch (dataTypeName) {
            case (EntityUtil.DataType.LOCAL_DATE_TIME):
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstant.DATE_FORMAT);
                return LocalDateTime.parse(objectString, formatter);
            case (EntityUtil.DataType.INTEGER):
                return Integer.valueOf(objectString);
        }
        throw new RuntimeException(String.format("convertStringToJava Exception: Data type %s not supported. " +
                "Can't convert %s to desired type", dataTypeName, objectString));
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
