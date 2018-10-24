package com.journaldev.other;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

@Builder
@Getter
public class ClientDataWrapper {
    private Integer customerId;
    private Integer productTypeId;
    private Integer amount;
    private Integer term;
    private LocalDateTime applicationDate;
}
