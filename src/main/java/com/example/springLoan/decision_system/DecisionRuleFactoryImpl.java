package com.example.springLoan.decision_system;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.enums.SettingName;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.service.ProductTypeSettingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class DecisionRuleFactoryImpl implements DecisionRuleFactory {

    ProductTypeSettingService productTypeSettingService;

    @Override
    public DecisionRule rulesForTestProductType(ProductRequestDTO productRequestDTO) {
        List<ProductTypeSetting> productTypeSettings = productTypeSettingService
                .findByProductType_Id(productRequestDTO.getProductTypeId());

        Integer minAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                SettingName.MIN_AMOUNT.toString());
        Integer maxAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                SettingName.MAX_AMOUNT.toString());
        Integer loanAmount = productRequestDTO.getAmount();

        Integer minTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                SettingName.MIN_TERM.toString());
        Integer maxTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                SettingName.MAX_TERM.toString());
        Integer loanTerm = productRequestDTO.getTerm();

        LocalTime minRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                SettingName.MIN_REJECTION_TIME.toString());
        LocalTime maxRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                SettingName.MAX_REJECTION_TIME.toString());
        LocalTime loanApplicationTime = productRequestDTO.getApplicationDate().toLocalTime();

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
