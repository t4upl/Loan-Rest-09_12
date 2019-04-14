package com.example.loanrestapi.service;

import com.example.loanrestapi.dto.ProductRequestDTO;
import com.example.loanrestapi.model.ProductSetting;
import java.util.Set;

public interface ProductSettingService {

  Set<ProductSetting> getProductSettings(ProductRequestDTO productRequestDTO);

  Set<ProductSetting> addExtensionTermToDueDate(Set<ProductSetting> productSettingSet);

}
