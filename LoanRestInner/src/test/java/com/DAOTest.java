package com;

import com.journaldev.dao.GenericInsertableDAO;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.factory.EntityFactory;
import com.journaldev.testDependencies.DAOTestDependencies;
import com.journaldev.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.List;

public class DAOTest {

    private static DAOTestDependencies daoTestDependencies;
    private static EntityFactory entityFactory;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        DAOTest.daoTestDependencies  = (DAOTestDependencies) context.getBean("daoTestDependencies");
        DAOTest.entityFactory = DAOTest.daoTestDependencies.getEntityFactory();
    }

    @Test
    public void autowiredTest() {
        Assert.notNull(daoTestDependencies);
        Assert.notNull(daoTestDependencies.getCustomerDAO());
    }

    @Test
    public void customerDAOSelectTest() {
        DAOselectTestTemplate(daoTestDependencies.getEntityFactory().getCustomer(TestUtil.customerId, "Johny"),
                daoTestDependencies.getCustomerDAO().findById(TestUtil.customerId));
    }

    @Test
    public void productDAOInsertTest() {
        DAOInsertTestTemplate(daoTestDependencies.getProductDAO(), daoTestDependencies.getEntityFactory().
                getProduct(-1, TestUtil.productTypeId, TestUtil.customerId));
    }

    @Test
    public void productTypeSettingDAOfindByIdTest(){
        DAOselectTestTemplate(daoTestDependencies.getEntityFactory().getProductTypeSetting(1,
                TestUtil.productTypeId,1,"1000"), daoTestDependencies.getProductTypeSettingDAO().
                findById(1));
    }

    @Test
    public void productTypeSettingDAOGetProductTypeSettingsByProductId() {
        ProductTypeSetting productTypeSetting = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(1, TestUtil.productTypeId, 1, "1000");
        ProductTypeSetting productTypeSetting2 = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(2, TestUtil.productTypeId, 2, "5000");
        ProductTypeSetting productTypeSetting3 = daoTestDependencies.getEntityFactory().
                getProductTypeSetting(3, TestUtil.productTypeId, 3, "3");

        List<ProductTypeSetting> productTypeSettings = daoTestDependencies.getProductTypeSettingDAO().
                getProductTypeSettingsByProductTypeId(TestUtil.productTypeId);

        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting));
        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting2));
        Assertions.assertTrue(productTypeSettings.contains(productTypeSetting3));
    }

    @Test
    public void productTypeSettingDAOgetCountTest() {
        long settingTypeCountForProjectType = daoTestDependencies.getProductTypeSettingDAO().
                getProductTypeSettingsByProductTypeId(TestUtil.productTypeId).size();

        Assertions.assertEquals(12, settingTypeCountForProjectType,
                "Failed on  ProductTypeSettingDAO.getCount()");
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
