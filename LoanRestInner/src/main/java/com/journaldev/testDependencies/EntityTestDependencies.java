package com.journaldev.testDependencies;

import com.journaldev.dao.ProductDAO;
import com.journaldev.dao.ProductSettingDAO;
import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.dao.SettingTypeDAO;
import com.journaldev.factory.EntityFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class EntityTestDependencies {

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private ProductTypeSettingDAO productTypeSettingDAO;

    @Autowired
    private SettingTypeDAO settingTypeDAO;

    @Autowired
    private ProductSettingDAO productSettingDAO;

    @Autowired
    private ProductDAO productDAO;
}
