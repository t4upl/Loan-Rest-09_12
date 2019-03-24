package com.example.springLoan.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductRequestDTO {
    private Integer customerId;
    private Integer productTypeId;
    private Integer amount;
    private Integer term;
    private LocalDateTime applicationDate;
}
