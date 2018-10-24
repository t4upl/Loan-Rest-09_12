package com.journaldev.manager;

import com.journaldev.dao.ProductSettingDAO;
import com.journaldev.entity.ProductSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductSettingManagerImpl implements ProductSettingManager {

    @Autowired
    ProductSettingDAO productSettingDAO;

    @Override
    public void insert(List<ProductSetting> productSettings) {
        productSettings.forEach(productSetting -> productSettingDAO.insert(productSetting));
    }
}
