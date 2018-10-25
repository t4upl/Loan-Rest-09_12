package com;

import com.journaldev.entity.Product;
import com.journaldev.entity.ProductSetting;
import com.journaldev.factory.EntityFactory;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.testDependencies.ManagerTestDependencies;
import com.journaldev.util.SettingTypeUtil;
import com.journaldev.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
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
    public void autowiredTest() {
        Assert.notNull(managerTestDependencies);
        Assert.notNull(managerTestDependencies.getProductSettingManager());
    }

    @Test
    public void productSettingManagerInsertTest() {
        int productId = managerTestDependencies.getProductDAO().insert(entityFactory.getProduct(
                -1, TestUtil.productTypeId, TestUtil.customerId)).getId();
        int settingTypeId1 = 1;
        int settingTypeId2 = 2;
        String value = "productSettingManagerInsertTest";

        List<ProductSetting> productSettings = new ArrayList<>();
        productSettings.add(entityFactory.getProductSetting(-1, productId, settingTypeId1, value));
        productSettings.add(entityFactory.getProductSetting(-1, productId, settingTypeId2, value));

        managerTestDependencies.getProductSettingDAO().deleteByProductIdAndSettingTypeId(productId, settingTypeId1);
        managerTestDependencies.getProductSettingDAO().deleteByProductIdAndSettingTypeId(productId, settingTypeId2);

        long countBeforeInsert = managerTestDependencies.getProductSettingDAO().getCount();
        managerTestDependencies.getProductSettingManager().insert(productSettings);
        long countAfterInsert = managerTestDependencies.getProductSettingDAO().getCount();
        Assertions.assertEquals(countBeforeInsert + 2, countAfterInsert);
    }

    @Test
    public void productManagerTakeLoanTest() {
        int productTypeId = TestUtil.productTypeId;
        ClientDataWrapper clientDataWrapper = TestUtil.getClientDataWrapperForLoan();

        long productCountBeforeLoan = managerTestDependencies.getProductDAO().getCount();
        long productSettingCountBeforeLoan = managerTestDependencies.getProductSettingDAO().getCount();
        long settingTypeCountForProjectType = managerTestDependencies.getProductTypeSettingDAO()
                .getProductTypeSettingsByProductTypeId(productTypeId).size();

        Product product = managerTestDependencies.getProductManager().applyForLoan(clientDataWrapper);

        Assertions.assertEquals(productCountBeforeLoan + 1,
                (long)managerTestDependencies.getProductDAO().getCount());
        Assertions.assertEquals(productSettingCountBeforeLoan + settingTypeCountForProjectType,
                (long)managerTestDependencies.getProductSettingDAO().getCount());

        Assertions.assertEquals(settingTypeCountForProjectType, managerTestDependencies.getProductSettingDAO()
                .findByProductId(product.getId()).size());
        ProductSetting productSettingAmount = managerTestDependencies.getProductSettingManager().
                getProductSettingBySettingTypeName(product.getId(), SettingTypeUtil.amount);
        Assertions.assertEquals(clientDataWrapper.getAmount().toString(), productSettingAmount.getValue());
    }

    @Test
    public void productManagerExtendLoanTest() {
        ClientDataWrapper clientDataWrapper = TestUtil.getClientDataWrapperForLoan();
        Product product = managerTestDependencies.getProductManager().applyForLoan(clientDataWrapper);

        LocalDateTime dueDateBeforeExtend = managerTestDependencies.getProductSettingManager().getDueDate(
                product.getId());
        int extensionTerm = managerTestDependencies.getProductSettingManager().getExtensionTerm(
                product.getId());
        managerTestDependencies.getProductSettingManager().extendLoan(product.getId());

        Assertions.assertEquals(dueDateBeforeExtend.plusDays(extensionTerm),
                managerTestDependencies.getProductSettingManager().getDueDate(product.getId()));
    }




}
