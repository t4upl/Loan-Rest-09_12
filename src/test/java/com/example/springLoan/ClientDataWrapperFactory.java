package com.example.springLoan;

import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.util.TestingUtil;
import com.example.springLoan.util.constant.ApplicationConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientDataWrapperFactory {

    public static ClientDataWrapper getClientDataWrapper(Integer amount, String applicationDate, Integer term) {
        return ClientDataWrapper.builder()
                .amount(amount)
                .applicationDate(LocalDateTime.parse(applicationDate,
                        DateTimeFormatter.ofPattern(ApplicationConstant.DATE_FORMAT)))
                .customerId(TestingUtil.CUSTOMER_ID)
                .productTypeId(TestingUtil.PRODUCT_TYPE_ID)
                .term(term)
                .build();
    }

}
