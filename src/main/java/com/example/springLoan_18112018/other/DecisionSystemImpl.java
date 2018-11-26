package com.example.springLoan_18112018.other;

import org.springframework.stereotype.Component;

@Component
public class DecisionSystemImpl implements DecisionSystem {

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        return false;
    }
}
