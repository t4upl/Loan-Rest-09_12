package com.example.springLoan.decision_system;

import com.example.springLoan.other.ClientDataWrapper;

public interface DecisionSystem {
    boolean isLoanGiven(ClientDataWrapper clientDataWrapper);
}
