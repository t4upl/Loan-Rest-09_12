package com.journaldev.dao;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductSettingPK;

import java.util.List;
import java.util.Optional;

public interface ProductSettingDAO extends GenericInsertableDAO<ProductSetting>, AbstractDAO<ProductSetting> {

    void deleteByProductIdAndSettingTypeId(ProductSettingPK productSettingPK);
    void deleteByProductIdAndSettingTypeId(int productId, int settingTypeId);


    ProductSetting findByProductIdAndSettingTypeId(int productId, int settingTypeId);
    Optional<ProductSetting> findById(ProductSettingPK productSettingPK);

    ProductSetting insert(ProductSetting productSetting);

    List<ProductSetting> findByProductId (int productId);

    void update (ProductSetting productSetting);
}
