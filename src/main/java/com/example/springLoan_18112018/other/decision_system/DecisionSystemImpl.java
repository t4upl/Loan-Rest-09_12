package com.example.springLoan_18112018.other.decision_system;

import com.example.springLoan_18112018.other.ClientDataWrapper;
import com.example.springLoan_18112018.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DecisionSystemImpl implements DecisionSystem {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        return DecisionRule.DecisionRuleBuilder
                .builder()
                .addFilter(DecisionFilterFactory.getAmountInRange(0.,0.,0.)) //amount
                .addFilter(DecisionFilterFactory.getAmountInRange(0,0,0)) //term
                .build()

                .checkRule();
    }


}
