package com.journaldev.manager;

import com.journaldev.entity.ProductSetting;

import java.util.List;

public interface ProductSettingManager {
    void insert(List<ProductSetting> productSettings);
    void extendLoan(int productId);
}
