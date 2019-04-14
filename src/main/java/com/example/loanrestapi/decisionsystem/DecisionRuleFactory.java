package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDto;

@SuppressWarnings("checkstyle:abbreviationaswordinnamecheck")
public interface DecisionRuleFactory {
  DecisionRule rulesForTestProductType(ProductRequestDto productRequestDto);
}
