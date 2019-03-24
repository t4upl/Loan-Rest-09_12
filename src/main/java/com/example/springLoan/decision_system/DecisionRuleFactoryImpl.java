package com.example.springLoan.decision_system;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.service.ProductTypeSettingService;
import com.example.springLoan.util.constant.EntityUtil;
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
                EntityUtil.Setting.MIN_AMOUNT);
        Integer maxAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
                EntityUtil.Setting.MAX_AMOUNT);
        Integer loanAmount = productRequestDTO.getAmount();

        Integer minTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, EntityUtil.Setting.MIN_TERM);
        Integer maxTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings, EntityUtil.Setting.MAX_TERM);
        Integer loanTerm = productRequestDTO.getTerm();

        LocalTime minRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                EntityUtil.Setting.MIN_REJECTION_TIME);
        LocalTime maxRejectionTime = productTypeSettingService.findAndGetAsLocalTime(productTypeSettings,
                EntityUtil.Setting.MAX_REJECTION_TIME);
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
