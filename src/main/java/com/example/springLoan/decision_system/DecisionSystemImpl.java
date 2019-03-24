package com.example.springLoan.decision_system;

import com.example.springLoan.dto.ProductRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecisionSystemImpl implements DecisionSystem {

    DecisionRuleFactory decisionRuleFactory;

    @Override
    public boolean isLoanGiven(ProductRequestDTO productRequestDTO) {
        DecisionRule decisionRule;
        switch (productRequestDTO.getProductTypeId()) {
            case 1:
                decisionRule = decisionRuleFactory.rulesForTestProductType(productRequestDTO);
                break;
            default:
                throw new RuntimeException(String.format("No loan application rule set for loan with " +
                    "productType: %d", productRequestDTO.getProductTypeId()));
        }
        return decisionRule.checkRule();
    }
}
