package com;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductSettingPK;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.factory.EntityFactory;
import com.journaldev.test.ManagerTestDependencies;
import com.journaldev.util.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class ManagerTest {

    static ManagerTestDependencies managerTestDependencies;
    static EntityFactory entityFactory;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ManagerTest.managerTestDependencies  = (ManagerTestDependencies) context.getBean("managerTestDependencies");
        ManagerTest.entityFactory = managerTestDependencies.getEntityFactory();
    }

    @Test
    public void AutowiredTest() {
        Assert.notNull(managerTestDependencies);
        Assert.notNull(managerTestDependencies.getProductSettingManager());
    }

    @Test
    public void productSettingManagerInsertTest() {
        int productId = 1;
        int settingTypeId1 = 1;
        int settingTypeId2 = 2;
        String value = "productSettingManagerInsertTest";

        List<ProductSetting> productSettings = new ArrayList<>();
        productSettings.add(entityFactory.getProductSetting(1, productId, settingTypeId1, value));
        productSettings.add(entityFactory.getProductSetting(1, productId, settingTypeId2, value));

        managerTestDependencies.getProductSettingDAO().delete(ProductSettingPK.builder()
                                                                          .settingTypeId(settingTypeId1)
                                                                          .productId(productId)
                                                                          .build());

        managerTestDependencies.getProductSettingDAO().delete(ProductSettingPK.builder()
                                                                          .settingTypeId(settingTypeId2)
                                                                          .productId(productId)
                                                                          .build());

        long countBeforeInsert = managerTestDependencies.getProductSettingDAO().getCount();
        managerTestDependencies.getProductSettingManager().insert(productSettings);
        long countAfterInsert = managerTestDependencies.getProductSettingDAO().getCount();
        Assertions.assertEquals(countBeforeInsert + 2, countAfterInsert);
    }

    @Test
    public void productManagerTakeLoanPassTest() {
        int productTypeId = 1;

        ClientDataWrapper clientDataWrapper = ClientDataWrapper.builder()
                                                               .amount(2000)
                                                               .applicationDate(AppUtil.stringToLocalDateTime(
                                                                       "2018-10-23 00:00:00"))
                                                               .customerId(1)
                                                               .productTypeId(productTypeId)
                                                               .term(10)
                                                               .build();

        long productCountBeforeLoan = managerTestDependencies.getProductDAO().getCount();
        long productSettingCountBeforeLoan = managerTestDependencies.getProductSettingDAO().getCount();
        long settingTypeCountForProjectType = managerTestDependencies.getProductTypeSettingDAO()
                .getCount(entityFactory.getProductTypeSetting(null, productTypeId, null, null));

        managerTestDependencies.getProductManager().takeLoan(clientDataWrapper);

        long productCountAfterLoan = managerTestDependencies.getProductDAO().getCount();
        long productSettingCountAfterLoan = managerTestDependencies.getProductSettingDAO().getCount();

        Assertions.assertEquals(productCountBeforeLoan + 1, productCountAfterLoan);
        Assertions.assertEquals(productSettingCountBeforeLoan + settingTypeCountForProjectType,
                                                                                    productSettingCountAfterLoan);

    }


}
