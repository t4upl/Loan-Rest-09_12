package com.example.springLoan.decision_system;

import com.example.springLoan.dto.ProductRequestDTO;

public interface DecisionRuleFactory {
    DecisionRule rulesForTestProductType(ProductRequestDTO productRequestDTO);
}
