package com.journaldev.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AppUtil {

    private static String datePattern = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);


    static public String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    static public LocalDateTime stringToLocalDateTime(String dateString){
        return LocalDateTime.parse(dateString, formatter);
    }




}
