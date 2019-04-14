package com.example.loanrestapi.service;

import com.example.loanrestapi.enums.DataTypeEnum;
import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.model.ProductTypeSetting;
import com.example.loanrestapi.repository.ProductTypeSettingRepository;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductTypeSettingServiceImpl implements ProductTypeSettingService {

  ProductTypeSettingRepository productTypeSettingRepository;

  @Override
  public Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings,
    SettingName key) {
    return Integer.parseInt(findAndGetAsObject(productTypeSettings, key, DataTypeEnum.INTEGER));
  }

  @Override
  public LocalTime findAndGetAsLocalTime(List<ProductTypeSetting> productTypeSettings,
    SettingName key) {
    return LocalTime.parse(findAndGetAsObject(productTypeSettings, key, DataTypeEnum.LOCAL_TIME));
  }

  @Override
  public Double findAndGetAsDouble(List<ProductTypeSetting> productTypeSettings, SettingName key) {
    return Double.parseDouble(findAndGetAsObject(productTypeSettings, key, DataTypeEnum.DOUBLE));
  }

  @Override
  public List<ProductTypeSetting> findByProductType_Id(Long productTypeId) {
    return productTypeSettingRepository.findByProductType_Id(productTypeId);
  }

  private String findAndGetAsObject(List<ProductTypeSetting> productTypeSettings,
    SettingName propertyKey,
    DataTypeEnum dataTypeKey) {
    ProductTypeSetting productTypeSetting = findProductTypeSettingByName(productTypeSettings,
      propertyKey);

    DataTypeEnum ptsDataType = productTypeSetting.getSetting().getDataType().getName();
    if (!ptsDataType.equals(dataTypeKey)) {
      throw new RuntimeException(
        String.format("Error casting productTypeSetting with key: %s of type: %s " +
          "to type: %s.", propertyKey, ptsDataType, dataTypeKey));
    }

    return productTypeSetting.getValue();
  }

  private ProductTypeSetting findProductTypeSettingByName(List<ProductTypeSetting> productSettings,
    SettingName key) {
    return productSettings
      .stream()
      .filter(productTypeSetting ->
        productTypeSetting.getSetting().getName().equals(key))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("findProductTypeSettingByName: " + key));
  }
}
