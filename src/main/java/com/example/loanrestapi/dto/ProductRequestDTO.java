package com.example.loanrestapi.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
