package com.journaldev.dao;

import com.journaldev.entity.ProductSetting;

import java.util.List;

public interface ProductSettingDAO extends GenericInsertableDAO<ProductSetting>, AbstractDAO<ProductSetting> {

    void deleteByProductIdAndSettingTypeId(int productId, int settingTypeId);

    ProductSetting findByProductIdAndSettingTypeId(int productId, int settingTypeId);

    ProductSetting insert(ProductSetting productSetting);

    List<ProductSetting> findByProductId (int productId);

    void update (ProductSetting productSetting);
}
