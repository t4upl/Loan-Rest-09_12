package com.example.loanrestapi.decisionsystem;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory providing implementations of DecisionFilter interface.
 */
@Component
class DecisionFilterFactory {

  static DecisionFilter valueInRange(Integer minAmount, Integer maxAmount, Integer value) {
    return new AmountInRangeDecisionFilter(minAmount.doubleValue(), maxAmount.doubleValue(),
                                           value.doubleValue());
  }

  static DecisionFilter outsideOfRejectionHours(Integer maxAmount, Integer loanAmount,
                                                LocalTime minRejectionDate,
                                                LocalTime maxRejectionDate,
                                                LocalTime applicationDate) {
    return new OutsideOfRejectionHours(maxAmount, loanAmount, minRejectionDate, maxRejectionDate,
                                       applicationDate);
  }

  @AllArgsConstructor
  private static class OutsideOfRejectionHours implements DecisionFilter {
    Integer maxAmount;
    Integer loanAmount;

    LocalTime minRejectionTime;
    LocalTime maxRejectionTime;
    LocalTime applicationTime;

    @Override
    public boolean isPassed() {
      return !(applicationTime.isAfter(minRejectionTime)
              && applicationTime.isBefore(maxRejectionTime)
                    && loanAmount.equals(maxAmount));
    }
  }

  @AllArgsConstructor
  private static class AmountInRangeDecisionFilter implements DecisionFilter {
    Double minAmount;
    Double maxAmount;
    Double loanAmount;

    @Override
    public boolean isPassed() {
      return !(loanAmount < minAmount || loanAmount > maxAmount);
    }
  }
}
