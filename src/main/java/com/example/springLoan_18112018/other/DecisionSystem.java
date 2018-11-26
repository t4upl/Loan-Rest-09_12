package com.example.springLoan_18112018.other;

import org.springframework.stereotype.Component;

public interface DecisionSystem {
    boolean isLoanGiven(ClientDataWrapper clientDataWrapper);
}
