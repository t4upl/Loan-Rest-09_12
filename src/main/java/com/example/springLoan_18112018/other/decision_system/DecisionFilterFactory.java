package com.example.springLoan_18112018.other.decision_system;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/***
 * Factory providing implementations of DecisionFilter interface
 */
@Component
public class DecisionFilterFactory {

    static public DecisionFilter getAmountInRange(Integer minAmount, Integer maxAmount, Integer value){
        return new AmountInRangeDecisionFilter(minAmount.doubleValue(), maxAmount.doubleValue(),
                value.doubleValue());
    }

    static public DecisionFilter getAmountInRange(Double minAmount, Double maxAmount, Double value){
        return new AmountInRangeDecisionFilter(minAmount, maxAmount, value);
    }

    static public  DecisionFilter getOutsideOfRejectionHours(Double maxAmount, Double loanAmount,
                                                             LocalDateTime minRejectionDate,
                                                             LocalDateTime maxRejectionDate,
                                                             LocalDateTime applicationDate){
        return new OutsideOfRejectionHours(maxAmount, loanAmount, minRejectionDate, maxRejectionDate, applicationDate);
    }

    @AllArgsConstructor
    private static class OutsideOfRejectionHours implements DecisionFilter {
        Double maxAmount;
        Double loanAmount;

        LocalDateTime minRejectionDate;
        LocalDateTime maxRejectionDate;
        LocalDateTime applicationDate;

        @Override
        public boolean isPassed() {
            return !(applicationDate.isAfter(minRejectionDate) && applicationDate.isBefore(maxRejectionDate)
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
