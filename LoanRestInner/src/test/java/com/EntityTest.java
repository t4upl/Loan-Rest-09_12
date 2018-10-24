package com;

import com.journaldev.entity.Product;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;
import com.journaldev.factory.EntityFactory;
import com.journaldev.testDependencies.EntityTestDependencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntityTest {

    private static EntityTestDependencies entityTestDependencies;
    private static EntityFactory entityFactory;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        EntityTest.entityTestDependencies = (EntityTestDependencies) context.getBean("entityTestDependencies");
        EntityTest.entityFactory = entityTestDependencies.getEntityFactory();
    }

    @Test
    public void autowiredTest() {
        Assertions.assertNotNull(entityTestDependencies);
        Assertions.assertNotNull(entityTestDependencies.getProductTypeSettingDAO());
    }

    @Test
    public void productTypeSettingGetSettingType(){
        ProductTypeSetting productTypeSetting = entityTestDependencies.getProductTypeSettingDAO().findById(1);

        Assertions.assertNotNull(productTypeSetting, "productTypeSetting is null");
        SettingType settingType = productTypeSetting.getSettingType();
        Assertions.assertEquals("1000", productTypeSetting.getValue());
        Assertions.assertNotNull(settingType);
        Assertions.assertEquals("min amount", settingType.getName());
    }

    @Test
    void productSettingGetSettingTypeTest(TestInfo testInfo) {

        Product product = entityTestDependencies.getProductDAO().insert(
                            entityFactory.getProduct(-1, 1, 1));

        ProductSetting productSetting = entityTestDependencies.getProductSettingDAO().findById(1);
        if (productSetting == null) {
            productSetting = entityTestDependencies.getProductSettingDAO().insert(
                             entityFactory.getProductSetting(1, product.getId(), 1,
                                     "productSettingGetSettingTypeTest"));

            //refresh many-to-one
            productSetting = entityTestDependencies.getProductSettingDAO().findById(productSetting.getId());
        }

        SettingType settingType = entityTestDependencies.getSettingTypeDAO().findById(1);
        Assertions.assertNotNull(settingType);
        Assertions.assertNotNull(settingType.getProductSettings(),
                testInfo.getDisplayName() + ": getProductSettings() is null");

        Assertions.assertNotNull(productSetting);
        Assertions.assertNotNull(productSetting.getSettingType(),
                        testInfo.getDisplayName() + ": getSettingType() is null");
    }
}
