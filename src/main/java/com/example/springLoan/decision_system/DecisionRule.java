package com.example.springLoan.decision_system;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/***
 * Class represents a set of business rules that customer's application for loan must pass
 * if customer should be given a loan.
 */
public class DecisionRule {

    @Getter
    private List<DecisionFilter> decisionFilterList;

    public boolean checkRule() {
        return decisionFilterList
                .stream()
                .allMatch(DecisionFilter::isPassed);
    }


    public DecisionRule() {
        this.decisionFilterList = new ArrayList<>();
    }

    public static class DecisionRuleBuilder {
        static DecisionRule decisionRule;

        private DecisionRuleBuilder(){
        };

        static public DecisionRuleBuilder builder() {
            decisionRule = new DecisionRule();
            return new DecisionRuleBuilder();
        }

        public DecisionRule build(){
            return decisionRule;
        }

        public DecisionRuleBuilder addFilter(DecisionFilter decisionFilter){
            decisionRule.getDecisionFilterList().add(decisionFilter);
            return this;
        }

    }
}
