package com.journaldev.test;

import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.dao.SettingTypeDAO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class EntityTestDependencies {

    @Autowired
    private ProductTypeSettingDAO productTypeSettingDAO;

    @Autowired
    private SettingTypeDAO settingTypeDAO;
}
