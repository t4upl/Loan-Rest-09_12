package com.journaldev.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static String datePattern = "yyyy-MM-dd HH:mm:ss";
    private static String timePattern = "HH:mm:ss";
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);


    static public String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(dateFormatter);
    }

    static public LocalDateTime getLocalDateTime(String dateString){
        return LocalDateTime.parse(dateString, dateFormatter);
    }

    static public LocalDateTime addDaysToToLocalDateTime(LocalDateTime localDateTime, int numberOfDays){
        return localDateTime.plusDays(numberOfDays);
    }

    static  public LocalTime getLocalTime(String timeString) {
        return LocalTime.parse(timeString, timeFormatter);
    }

    static  public LocalDateTime getLocalDateTimeWithLocalTime(LocalDateTime localDateTime, LocalTime localTime) {
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                                localTime.getHour(),localTime.getMinute(),localTime.getSecond());
    }



}
