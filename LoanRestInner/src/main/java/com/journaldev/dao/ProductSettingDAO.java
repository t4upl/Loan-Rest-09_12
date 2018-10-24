package com.journaldev.dao;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductSettingPK;

import java.util.Optional;

public interface ProductSettingDAO extends GenericInsertableDAO<ProductSetting> {
    void delete (ProductSettingPK productSettingPK);
    Optional<ProductSetting> findById(ProductSettingPK productSettingPK);
    ProductSetting insert(ProductSetting productSetting);
}
