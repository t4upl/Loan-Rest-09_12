package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDTO;

public interface DecisionRuleFactory {
  DecisionRule rulesForTestProductType(ProductRequestDTO productRequestDTO);
}
