package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecisionSystemImpl implements DecisionSystem {

  DecisionRuleFactory decisionRuleFactory;

  @Override
  public boolean isLoanGiven(ProductRequestDto productRequestDto) {
    DecisionRule decisionRule = null;
    Long productTypeId = productRequestDto.getProductTypeId();
    if (productTypeId.equals(1L)) {
      decisionRule = decisionRuleFactory.rulesForTestProductType(productRequestDto);
    }

    if (decisionRule != null) {
      return decisionRule.checkRule();
    }

    throw new RuntimeException(String.format("No loan application rule set for loan with"
      + " productType: %d", productRequestDto.getProductTypeId()));
  }
}
