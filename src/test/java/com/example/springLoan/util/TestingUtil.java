package com.example.springLoan.util;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.util.constant.ApplicationConstant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TestingUtil {

    public static String readFileAsString(String filePath) {
        File file = new File(TestingUtil.class.getClassLoader()
                .getResource(filePath).getFile());
        List<String> strings = null;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings.stream().collect(Collectors.joining("\n"));
    }

    public static ProductRequestDTO getProductRequestDTO(Integer amount, String applicationDate, Integer term) {
        return ProductRequestDTO.builder()
                .amount(amount)
                .applicationDate(LocalDateTime.parse(applicationDate,
                        DateTimeFormatter.ofPattern(ApplicationConstant.DATE_FORMAT)))
                .customerId(1)
                .productTypeId(1)
                .term(term)
                .build();
    }
}
