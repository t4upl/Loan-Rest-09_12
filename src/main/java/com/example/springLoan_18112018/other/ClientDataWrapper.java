package com.example.springLoan_18112018.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ClientDataWrapper {
    private Integer customerId;
    private Integer productTypeId;
    private Integer amount;
    private Integer term;
    private LocalDateTime applicationDate;
}
