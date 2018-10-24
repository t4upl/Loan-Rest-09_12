package com;

import com.journaldev.dao.*;
import com.journaldev.entity.ProductSettingPK;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.test.DAOTestDependencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.List;

public class DAOTest {

    static DAOTestDependencies daoTestDependencies;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        DAOTest.daoTestDependencies  = (DAOTestDependencies) context.getBean("daoTestDependencies");
    }

    @Test
    public void AutowiredTest() {
        Assert.notNull(daoTestDependencies);
        Assert.notNull(daoTestDependencies.getCustomerDAO());
    }

    @Test
    public void customerDAOSelectTest() {
        DAOselectTestTemplate(daoTestDependencies.getEntityFactory().getCustomer(1, "Johny"),
                daoTestDependencies.getCustomerDAO().findById(1));
    }

    @Test
    public void productDAOSelectTest() {
        DAOselectTestTemplate(daoTestDependencies.getEntityFactory().getProduct(1,1, 1),
                daoTestDependencies.getProductDAO().findById(1));
    }

    @Test
    public void productDAOInsertTest() {
        DAOInsertTestTemplate(daoTestDependencies.getProductDAO(),
                daoTestDependencies.getEntityFactory().getProduct(1, 1, 1));
    }

    @Test
    public void productTypeSettingDAOfindByIdTest(){
        DAOselectTestTemplate(
                daoTestDependencies.getEntityFactory().
                        getProductTypeSetting(1,1,1,"1000"),
                daoTestDependencies.getProductTypeSettingDAO().findById(1));
    }

    @Test
    public void productTypeSettingDAOGetProductTypeSettingsByProductId() {
        int productTypeId = 1;
        ProductTypeSetting productTypeSetting = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(1, productTypeId, 1, "1000");
        ProductTypeSetting productTypeSetting2 = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(2, productTypeId, 2, "5000");
        ProductTypeSetting productTypeSetting3 = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(3, productTypeId, 3, "3");

        List<ProductTypeSetting> productTypeSettings = daoTestDependencies.getProductTypeSettingDAO().
                                                            getProductTypeSettingsByProductId(productTypeId);

        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting));
        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting2));
        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting3));
    }

    @Test
    public void productSettingDAOInsertTest() {
        int productId = 1;
        int settingTypeId = 1;
        daoTestDependencies.getProductSettingDAO().delete(ProductSettingPK.builder().productId(productId)
                                                            .settingTypeId(settingTypeId).build());

        DAOInsertTestTemplate(daoTestDependencies.getProductSettingDAO(),
                daoTestDependencies.getEntityFactory().
                        getProductSetting(1, productId, settingTypeId,"test"));
    }

    @Test
    public void settingTypeDAOinsertTest() {
//
//        SettingType settingType = daoTestDependencies.getEntityFactory().
//        SettingType settingType = daoTestDependencies.getSettingTypeDAO().insert();
//        ProductTypeSetting productTypeSetting = daoTestDependencies.getSettingTypeDAO().insert();
    }

    private <T> void DAOInsertTestTemplate(GenericInsertableDAO<T> genericInsertableDAO, T t) {
        long productCountBeforeInsert = genericInsertableDAO.getCount();
        genericInsertableDAO.insert(t);
        long productCountAfterInsert = genericInsertableDAO.getCount();
        Assertions.assertEquals(productCountBeforeInsert + 1, productCountAfterInsert);
    }

    private void DAOselectTestTemplate(Object object, Object object2) {
        Assertions.assertEquals(object, object2);
    }

}
