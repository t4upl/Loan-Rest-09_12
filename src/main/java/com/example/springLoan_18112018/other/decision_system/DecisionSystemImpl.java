package com.example.springLoan_18112018.other.decision_system;

import com.example.springLoan_18112018.model.ProductTypeSetting;
import com.example.springLoan_18112018.other.ClientDataWrapper;
import com.example.springLoan_18112018.repository.ProductTypeRepository;
import com.example.springLoan_18112018.repository.ProductTypeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DecisionSystemImpl implements DecisionSystem {

    @Autowired
    ProductTypeSettingRepository productTypeSettingRepository;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        List<ProductTypeSetting> productTypeSettings = productTypeSettingRepository
                .findByProductType_Id(clientDataWrapper.getProductTypeId());

        Double minAmount = 0.;
        Double maxAmount = 0.;
        Double loanAmount = 0.;

        Integer minTerm = 0;
        Integer maxTerm = 0;
        Integer loanTerm = 0;

        LocalDateTime localDateTime = LocalDateTime.now();

        return DecisionRule.DecisionRuleBuilder
                .builder()
                .addFilter(DecisionFilterFactory.getAmountInRange(0.,0.,0.)) //amount
                .addFilter(DecisionFilterFactory.getAmountInRange(0,0,0)) //term
                // don't accept if between hours and max amount is asked
                .addFilter(DecisionFilterFactory.getOutsideOfRejectionHours(0., 0.,
                        localDateTime, localDateTime, localDateTime))
                .build()
                .checkRule();
    }


}
