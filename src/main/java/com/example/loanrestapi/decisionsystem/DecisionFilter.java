package com.example.loanrestapi.decisionsystem;

public interface DecisionFilter {

  /**
   * Return true if there are no objection to give loan.
 */
  boolean isPassed();
}
