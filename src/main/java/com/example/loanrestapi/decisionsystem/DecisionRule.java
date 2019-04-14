package com.example.loanrestapi.decisionsystem;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Class represents a set of business rules that customer's application for loan must pass
 * if customer should be given a loan.
 */
class DecisionRule {

  @Getter
  private List<DecisionFilter> decisionFilterList;

  boolean checkRule() {
    return decisionFilterList
            .stream()
            .allMatch(DecisionFilter::isPassed);
  }


  DecisionRule() {
    this.decisionFilterList = new ArrayList<>();
  }

  static class DecisionRuleBuilder {
    static DecisionRule decisionRule;

    private DecisionRuleBuilder() {}

    static DecisionRuleBuilder builder() {
      decisionRule = new DecisionRule();
      return new DecisionRuleBuilder();
    }

    DecisionRule build() {
      return decisionRule;
    }

    DecisionRuleBuilder addFilter(DecisionFilter decisionFilter) {
      decisionRule.getDecisionFilterList().add(decisionFilter);
      return this;
    }
  }
}
