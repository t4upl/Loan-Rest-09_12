package com.journaldev.manager;

import com.journaldev.entity.ProductSetting;
import com.journaldev.util.DateTimeUtil;
import com.journaldev.util.SettingTypeUtil;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductSettingManager {
    void insert(List<ProductSetting> productSettings);
    void extendLoan(int productId);
    ProductSetting getProductSettingBySettingTypeName(int productId, String productSettingName);
    LocalDateTime getDueDate(int productId);
    int getExtensionTerm(int productId);
}
