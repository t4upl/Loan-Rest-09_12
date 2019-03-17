package com.example.springLoan.decision_system;

import com.example.springLoan.other.ClientDataWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecisionSystemImpl implements DecisionSystem {

    DecisionRuleFactory decisionRuleFactory;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        DecisionRule decisionRule;
        switch (clientDataWrapper.getProductTypeId()) {
            case 1:
                decisionRule = decisionRuleFactory.rulesForTestProductType(clientDataWrapper);
                break;
            default:
                throw new RuntimeException(String.format("No loan application rule set for loan with " +
                    "productType: %d", clientDataWrapper.getProductTypeId()));
        }
        return decisionRule.checkRule();
    }
}
