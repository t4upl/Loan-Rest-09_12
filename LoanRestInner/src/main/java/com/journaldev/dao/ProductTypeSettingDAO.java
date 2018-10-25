package com.journaldev.dao;

import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;

import java.util.List;

public interface ProductTypeSettingDAO extends AbstractDAO<ProductTypeSetting> {
    List<ProductTypeSetting> getProductTypeSettingsByProductTypeId(int projectTypeId);
}
