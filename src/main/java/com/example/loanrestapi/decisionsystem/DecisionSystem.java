package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDTO;

public interface DecisionSystem {
  boolean isLoanGiven(ProductRequestDTO productRequestDTO);
}
