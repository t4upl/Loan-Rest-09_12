package com.example.loanrestapi.service;

import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.model.ProductSetting;
import java.util.Set;

public interface ProductSettingService {

  Set<ProductSetting> getProductSettings(ProductRequestDto productRequestDto);

  Set<ProductSetting> addExtensionTermToDueDate(Set<ProductSetting> productSettingSet);

}
