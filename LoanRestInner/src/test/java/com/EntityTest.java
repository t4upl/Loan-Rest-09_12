package com;

import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;
import com.journaldev.test.EntityTestDependencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntityTest {

    static EntityTestDependencies entityTestDependencies;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        EntityTest.entityTestDependencies = (EntityTestDependencies) context.getBean("entityTestDependencies");
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

//        System.out.println(entityTestDependencies.getProductTypeSettingDAO().findById(1));
//        System.out.println(entityTestDependencies.getProductTypeSettingDAO().findById(1).getSettingType());
//
//        System.out.println(entityTestDependencies.getProductTypeSettingDAO().findById(2));
//        System.out.println(entityTestDependencies.getProductTypeSettingDAO().findById(2).getSettingType());

        SettingType settingType = productTypeSetting.getSettingType();
        Assertions.assertEquals("1000", productTypeSetting.getValue());
        Assertions.assertNotNull(settingType);
        Assertions.assertEquals("min amount", settingType.getName());
    }

}
