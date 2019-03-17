package com.example.springLoan.decision_system;

/**
 * Interface
 */
public interface DecisionFilter {

    /**
     * Return true if there are no objection to give loan
     */
    boolean isPassed();
}
