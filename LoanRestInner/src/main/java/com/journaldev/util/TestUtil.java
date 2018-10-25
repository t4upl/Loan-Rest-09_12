package com.journaldev.util;

import com.journaldev.other.ClientDataWrapper;

public class TestUtil {

    public static final int productTypeId = 1;
    public static final int customerId = 1;

    public static ClientDataWrapper getClientDataWrapperForLoan() {
        return ClientDataWrapper.builder()
                                .amount(2000)
                                .applicationDate(DateTimeUtil.getLocalDateTime("2018-10-23 00:00:00"))
                                .customerId(1)
                                .productTypeId(1)
                                .term(10)
                                .build();
    }
}
