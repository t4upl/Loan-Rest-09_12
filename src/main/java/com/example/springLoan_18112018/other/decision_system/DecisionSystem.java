package com.example.springLoan_18112018.other.decision_system;

import com.example.springLoan_18112018.other.ClientDataWrapper;

public interface DecisionSystem {
    boolean isLoanGiven(ClientDataWrapper clientDataWrapper);
}
