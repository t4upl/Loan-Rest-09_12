package com.journaldev.testDependencies;

import com.journaldev.dao.*;
import com.journaldev.factory.EntityFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class DAOTestDependencies {

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductTypeSettingDAO productTypeSettingDAO;

    @Autowired
    private ProductSettingDAO productSettingDAO;

    @Autowired
    private SettingTypeDAO settingTypeDAO;
}
