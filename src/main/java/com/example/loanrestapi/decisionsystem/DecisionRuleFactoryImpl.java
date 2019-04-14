package com.example.loanrestapi.decisionsystem;

import com.example.loanrestapi.dto.ProductRequestDTO;
import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.model.ProductTypeSetting;
import com.example.loanrestapi.service.ProductTypeSettingService;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecisionRuleFactoryImpl implements DecisionRuleFactory {

  ProductTypeSettingService productTypeSettingService;

  @Override
  public DecisionRule rulesForTestProductType(ProductRequestDTO productRequestDTO) {
    List<ProductTypeSetting> productTypeSettings = productTypeSettingService.findByProductType_Id(
        productRequestDTO.getProductTypeId());

    Integer minAmount = productTypeSettingService.findAndGetAsInteger(
        productTypeSettings, SettingName.MIN_AMOUNT);
    Integer maxAmount = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
        SettingName.MAX_AMOUNT);
    Integer loanAmount = productRequestDTO.getAmount();

    Integer minTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
        SettingName.MIN_TERM);
    Integer maxTerm = productTypeSettingService.findAndGetAsInteger(productTypeSettings,
        SettingName.MAX_TERM);
    Integer loanTerm = productRequestDTO.getTerm();

    LocalTime minRejectionTime = productTypeSettingService
        .findAndGetAsLocalTime(productTypeSettings,
        SettingName.MIN_REJECTION_TIME);
    LocalTime maxRejectionTime = productTypeSettingService
        .findAndGetAsLocalTime(productTypeSettings, SettingName.MAX_REJECTION_TIME);
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
