package com.journaldev.test;

import com.journaldev.dao.*;
import com.journaldev.manager.ProductManager;
import com.journaldev.manager.ProductSettingManager;
import com.journaldev.factory.EntityFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class ManagerTestDependencies {

    @Autowired
    private EntityFactory entityFactory;


    //------------------------------------------
    //Manager
    @Autowired
    private ProductSettingManager productSettingManager;

    @Autowired
    private ProductManager productManager;


    //------------------------------------------
    //DAO
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
