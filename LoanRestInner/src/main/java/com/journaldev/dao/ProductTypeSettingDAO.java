package com.journaldev.dao;

import com.journaldev.entity.ProductTypeSetting;

import java.util.List;

public interface ProductTypeSettingDAO extends AbstractDAO<ProductTypeSetting> {
    List<ProductTypeSetting> getProductTypeSettingsByProductTypeId(int projectTypeId);
}
