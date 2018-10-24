package com;

import com.journaldev.entity.Product;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.factory.EntityFactory;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.other.DecisionSystem;
import com.journaldev.testDependencies.OtherTestDependencies;
import com.journaldev.util.FilterUtil;
import com.journaldev.util.DateTimeUtil;
import com.journaldev.util.SettingTypeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.List;

public class OtherTest {

    private static OtherTestDependencies otherTestDependencies;
    private static EntityFactory entityFactory;

    @BeforeAll
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        OtherTest.otherTestDependencies  = (OtherTestDependencies) context.getBean("otherTestDependencies");
        OtherTest.entityFactory = otherTestDependencies.getEntityFactory();
    }

    @Test
    public void AutowiredTest() {
        Assert.notNull(otherTestDependencies);
        Assert.notNull(otherTestDependencies.getDecisionSystem());
    }

    @Test
    public void decisionSystemIsLoanGivenTest() {
        DecisionSystem decisionSystem = otherTestDependencies.getDecisionSystem();
        ClientDataWrapper clientDataWrapper = FilterUtil.getClientDataWrapperForLoan();

        Assertions.assertTrue(decisionSystem.isLoanGiven(clientDataWrapper));

        Product product = otherTestDependencies.getProductDAO().insert(entityFactory.getProduct(
                                                                        -1, 1 ,1));

        List<ProductTypeSetting> productTypeSettings = otherTestDependencies.getProductTypeSettingDAO().findByExample(
                entityFactory.getProductTypeSetting(null, 1, null, null));

        int maxAmount = Integer.parseInt(FilterUtil.findProductTypeSettingByValue(
                productTypeSettings, SettingTypeUtil.maxAmount).getValue());

        LocalTime rejectionTimeMax = DateTimeUtil.getLocalTime(FilterUtil.findProductTypeSettingByValue(
                productTypeSettings, SettingTypeUtil.maxRejectionHour).getValue());

        LocalTime rejectionTime = LocalTime.of(rejectionTimeMax.getHour() -1, 0, 0);

        clientDataWrapper.setAmount(maxAmount);
        clientDataWrapper.setApplicationDate(DateTimeUtil.getLocalDateTimeWithLocalTime(clientDataWrapper.getApplicationDate(),
                rejectionTime));
    }
}
