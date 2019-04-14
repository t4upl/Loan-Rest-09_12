package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecisionSystemImpl implements DecisionSystem {

  DecisionRuleFactory decisionRuleFactory;

  @Override
  public boolean isLoanGiven(ProductRequestDTO productRequestDTO) {
    DecisionRule decisionRule = null;
    Long productTypeId = productRequestDTO.getProductTypeId();
    if (productTypeId.equals(1L)) {
      decisionRule = decisionRuleFactory.rulesForTestProductType(productRequestDTO);
    }

    if (decisionRule != null) {
      return decisionRule.checkRule();
    }

    throw new RuntimeException(String.format("No loan application rule set for loan with"
      + " productType: %d", productRequestDTO.getProductTypeId()));
  }
}
