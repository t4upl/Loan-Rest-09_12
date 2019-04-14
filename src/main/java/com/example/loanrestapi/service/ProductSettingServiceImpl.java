package com.example.loanrestapi.service;

import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.enums.DataTypeEnum;
import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.factory.AbstractFactory;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductTypeSetting;
import com.example.loanrestapi.repository.ProductSettingRepository;
import com.example.loanrestapi.util.FilterUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSettingServiceImpl implements ProductSettingService {

  ProductSettingRepository productSettingRepository;
  ProductTypeSettingService productTypeSettingService;
  AbstractFactory abstractFactory;

  @Override
  public Set<ProductSetting> getProductSettings(ProductRequestDto productRequestDto) {
    List<ProductTypeSetting> productTypeSettings = productTypeSettingService
        .findByProductType_Id(productRequestDto.getProductTypeId());

    return productTypeSettings
      .stream()
      .map(
        pts -> mapProductTypeSettingToProductSetting(pts, productRequestDto, productTypeSettings))
      .collect(Collectors.toSet());
  }

  @Override
  public Set<ProductSetting> addExtensionTermToDueDate(Set<ProductSetting> productSettingSet) {
    ProductSetting termProductSetting = findProductSettingBySettingName(productSettingSet,
        SettingName.EXTENSION_TERM);
    ProductSetting dueDateProductSetting = findProductSettingBySettingName(productSettingSet,
        SettingName.DUE_DATE);
    LocalDateTime dueDate = (LocalDateTime) FilterUtil.convertStringToJava(
        dueDateProductSetting.getValue(), DataTypeEnum.LOCAL_DATE_TIME.toString());

    Integer extensionTerm = (Integer) FilterUtil.convertStringToJava(termProductSetting.getValue(),
        DataTypeEnum.INTEGER.toString());

    dueDateProductSetting.setValue(FilterUtil.convertJavaToString(dueDate.plusDays(extensionTerm),
        DataTypeEnum.LOCAL_DATE_TIME.toString()));
    return productSettingSet;
  }

  private ProductSetting findProductSettingBySettingName(Set<ProductSetting> productSettingSet,
      SettingName name) {
    return productSettingSet.stream()
      .filter(x -> name.equals(x.getSetting().getName()))
      .findFirst()
      .orElseThrow(
        () -> new RuntimeException(String.format("findProductSettingBySettingName exception: "
          + "no ProductSetting with name: %s", name)));
  }


  private ProductSetting mapProductTypeSettingToProductSetting(
      ProductTypeSetting productTypeSetting, ProductRequestDto productRequestDto,
      List<ProductTypeSetting> productTypeSettings) {
    if (!productTypeSetting.getSetting().getIsRuntimeInput()) {
      return getProductSetting(productTypeSetting, productTypeSetting.getValue());
    }

    return getProductSetting(productTypeSetting,
      getValueForProductSetting(productTypeSetting, productRequestDto, productTypeSettings));
  }

  private ProductSetting getProductSetting(ProductTypeSetting productTypeSetting, String value) {
    return abstractFactory.getProductSettingFactory()
      .getProductSetting(null, value, null, productTypeSetting.getSetting());
  }

  private String getValueForProductSetting(ProductTypeSetting productTypeSetting,
      ProductRequestDto productRequestDto,
      List<ProductTypeSetting> productTypeSettings) {

    String dataTypeName = productTypeSetting.getSetting().getDataType().getName().toString();
    Optional<Object> javaObjectOpt = Optional.empty();

    switch (productTypeSetting.getSetting().getName()) {
      case APPLICATION_DATE:
        javaObjectOpt = Optional.of(productRequestDto.getApplicationDate());
        break;
      case AMOUNT:
        javaObjectOpt = Optional.of(productRequestDto.getAmount());
        break;
      case DUE_DATE:
        javaObjectOpt = Optional.of(productRequestDto.getApplicationDate().plusDays(
          productRequestDto.getTerm()));
        break;
      case AMOUNT_TO_PAY:
        double rateOfIntrest = productTypeSettingService
            .findAndGetAsDouble(productTypeSettings, SettingName.RATE_OF_INTEREST);
        javaObjectOpt = Optional.of(Double.valueOf(productRequestDto.getAmount())
          * (1 + rateOfIntrest / 100));
        break;
      case TERM:
        javaObjectOpt = Optional.of(productRequestDto.getTerm());
        break;
      default:
        break;
    }

    if (javaObjectOpt.isPresent()) {
      return FilterUtil.convertJavaToString(javaObjectOpt.get(), dataTypeName);
    }
    throw new RuntimeException(
      String.format("getValueForProductSetting - case not found for SettingName with name %s",
        productTypeSetting.getSetting().getName()));
  }
}
