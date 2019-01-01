package com.example.springLoan.other;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientDataWrapper {
    private Integer customerId;
    private Integer productTypeId;
    private Integer amount;
    private Integer term;
    private LocalDateTime applicationDate;
}
