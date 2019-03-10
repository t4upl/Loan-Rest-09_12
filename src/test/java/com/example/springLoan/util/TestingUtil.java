package com.example.springLoan.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TestingUtil {

    public static final Integer CUSTOMER_ID = 1;
    public static final Integer PRODUCT_TYPE_ID = 1;

    public static String readFileAsString(String filePath) {
        File file = new File(TestingUtil.class.getClassLoader()
                .getResource("test/json/client_data_wrapper/client_data_wrapper.json").getFile());
        List<String> strings = null;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings.stream().collect(Collectors.joining("\n"));
    }
}
