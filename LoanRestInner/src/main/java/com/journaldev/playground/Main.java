package com.journaldev.playground;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        RestTemplate rest = new RestTemplate();
        final String url = "http://yarpo.pl/2017/06/19/rest-owe-zadania-w-java-spring-z-wykorzystaniem-resttemplate/";
        String res = rest.getForObject(url, String.class, new HashMap<>());

        System.out.println(res);

    }
}
