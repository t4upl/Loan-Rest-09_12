package com.example.springLoan.decision_system;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.service.ProductTypeSettingService;
import com.example.springLoan.util.constant.SettingConstant;
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
                SettingConstant.MIN_AMOUNT);
        Integer maxAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                SettingConstant.MAX_AMOUNT);
        Integer loanAmount = productRequestDTO.getAmount();

        Integer minTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, SettingConstant.MIN_TERM);
        Integer maxTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, SettingConstant.MAX_TERM);
        Integer loanTerm = productRequestDTO.getTerm();

        LocalTime minRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                SettingConstant.MIN_REJECTION_TIME);
        LocalTime maxRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                SettingConstant.MAX_REJECTION_TIME);
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
