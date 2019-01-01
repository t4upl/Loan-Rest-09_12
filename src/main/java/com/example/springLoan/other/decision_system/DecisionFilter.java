package com.example.springLoan.other.decision_system;

/**
 * Interface
 */
public interface DecisionFilter {

    /**
     * Return true if there are no objection to give loan
     */
    boolean isPassed();
}
