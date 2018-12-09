package com;

import com.journaldev.entity.Product;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;
import com.journaldev.factory.EntityFactory;
import com.journaldev.test.EntityTestDependencies;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntityTest {

    private static EntityTestDependencies entityTestDependencies;
    private static EntityFactory entityFactory;

    @BeforeClass
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        EntityTest.entityTestDependencies = (EntityTestDependencies) context.getBean("entityTestDependencies");
        EntityTest.entityFactory = entityTestDependencies.getEntityFactory();
    }

    @Test
    public void autowiredTest() {
        Assert.assertNotNull(entityTestDependencies);
        Assert.assertNotNull(entityTestDependencies.getProductTypeSettingDAO());
    }

    @Test
    public void productTypeSettingGetSettingType(){
        ProductTypeSetting productTypeSetting = entityTestDependencies.getProductTypeSettingDAO().findById(1);

        Assert.assertNotNull(productTypeSetting);
        SettingType settingType = productTypeSetting.getSettingType();
        Assert.assertEquals("1000", productTypeSetting.getValue());
        Assert.assertNotNull(settingType);
        Assert.assertEquals("min amount", settingType.getName());
    }

    @Test
    void productSettingGetSettingTypeTest() {
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
        Assert.assertNotNull(settingType);
        Assert.assertNotNull(settingType.getProductSettings());

        Assert.assertNotNull(productSetting);
        Assert.assertNotNull(productSetting.getSettingType());
    }
}
