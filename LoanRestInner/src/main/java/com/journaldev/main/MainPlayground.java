package com.journaldev.main;

import com.journaldev.util.DateTimeUtil;

import java.time.LocalTime;

public class MainPlayground {

    public static void main(String[] args) {

        LocalTime localTime = DateTimeUtil.getLocalTime("06:51:13");

        System.out.println(localTime);
        System.out.println(localTime.getHour());
        System.out.println(localTime.getMinute());
        System.out.println(localTime.getSecond());

        System.out.println("END");
    }
}
