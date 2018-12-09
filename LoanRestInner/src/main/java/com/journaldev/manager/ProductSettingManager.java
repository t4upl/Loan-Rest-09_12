package com.journaldev.manager;

import com.journaldev.entity.ProductSetting;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductSettingManager {
    void insert(List<ProductSetting> productSettings);
    void extendLoan(int productId);
    ProductSetting getProductSettingBySettingTypeName(int productId, String productSettingName);
    LocalDateTime getDueDate(int productId);
    int getExtensionTerm(int productId);
}
