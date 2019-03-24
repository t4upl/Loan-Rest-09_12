package com.example.springLoan.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductRequestDTO {
    private Long customerId;
    private Long productTypeId;
    private Integer amount;
    private Integer term;
    private LocalDateTime applicationDate;
}
