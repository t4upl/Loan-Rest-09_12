package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDto;

public interface DecisionSystem {
  boolean isLoanGiven(ProductRequestDto productRequestDto);
}
