package com.journaldev.dao;

import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;

import java.util.List;

public interface ProductTypeSettingDAO {
    ProductTypeSetting findById(int id);
    List<ProductTypeSetting> getProductTypeSettingsByProductId(int productTypeId);
    Long getCount(ProductTypeSetting productTypeSetting);

}
