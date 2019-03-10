package com.example.springLoan.other.decision_system;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.ProductTypeSettingRepository;
import com.example.springLoan.service.ProductTypeSettingService;
import com.example.springLoan.util.constant.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class DecisionSystemImpl implements DecisionSystem {

    @Autowired
    private ProductTypeSettingRepository productTypeSettingRepository;

    @Autowired
    private ProductTypeSettingService productTypeSettingService;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        DecisionRule decisionRule;
        switch (clientDataWrapper.getProductTypeId()) {
            case 1:
                decisionRule = rulesForProductTypeWithId1(clientDataWrapper);
                break;
            default:
                throw new RuntimeException(String.format("No loan application rule set for loan with " +
                    "productType: %d", clientDataWrapper.getProductTypeId()));
        }
        return decisionRule.checkRule();
    }

    private DecisionRule rulesForProductTypeWithId1(ClientDataWrapper clientDataWrapper){
        List<ProductTypeSetting> productTypeSettings = productTypeSettingRepository
                .findByProductType_Id(clientDataWrapper.getProductTypeId());

        Integer minAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                EntityUtil.Setting.MIN_AMOUNT);
        Integer maxAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                EntityUtil.Setting.MAX_AMOUNT);
        Integer loanAmount = clientDataWrapper.getAmount();

        Integer minTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, EntityUtil.Setting.MIN_TERM);
        Integer maxTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, EntityUtil.Setting.MAX_TERM);
        Integer loanTerm = clientDataWrapper.getTerm();

        LocalTime minRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                EntityUtil.Setting.MIN_REJECTION_TIME);
        LocalTime maxRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                EntityUtil.Setting.MAX_REJECTION_TIME);
        LocalTime loanApplicationTime = clientDataWrapper.getApplicationDate().toLocalTime();

        return DecisionRule.DecisionRuleBuilder
                .builder()
                .addFilter(DecisionFilterFactory.valueInRange(minAmount, maxAmount, loanAmount)) //amount
                .addFilter(DecisionFilterFactory.valueInRange(minTerm, maxTerm, loanTerm)) //term
                // don't accept if between hours and max amount is asked
                .addFilter(DecisionFilterFactory.outsideOfRejectionHours(maxAmount, loanAmount,
                        minRejectionTime, maxRejectionTime, loanApplicationTime))
                .build();
    }


}
