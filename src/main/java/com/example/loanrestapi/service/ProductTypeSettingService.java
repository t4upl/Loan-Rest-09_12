package com.example.loanrestapi.service;

import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.model.ProductTypeSetting;
import java.time.LocalTime;
import java.util.List;

public interface ProductTypeSettingService {

  Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, SettingName key);

  LocalTime findAndGetAsLocalTime(List<ProductTypeSetting> productTypeSettings, SettingName key);

  Double findAndGetAsDouble(List<ProductTypeSetting> productTypeSettings, SettingName key);

  List<ProductTypeSetting> findByProductType_Id(Long productTypeId);
}
