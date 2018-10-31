package com;

import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.factory.EntityFactory;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.other.DecisionSystem;
import com.journaldev.test.OtherTestDependencies;
import com.journaldev.util.FilterUtil;
import com.journaldev.util.DateTimeUtil;
import com.journaldev.util.SettingTypeUtil;
import com.journaldev.util.TestUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalTime;
import java.util.List;

public class OtherTest {

    private static OtherTestDependencies otherTestDependencies;
    private static EntityFactory entityFactory;

    @BeforeClass
    public static void beforeAllTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        OtherTest.otherTestDependencies  = (OtherTestDependencies) context.getBean("otherTestDependencies");
        OtherTest.entityFactory = otherTestDependencies.getEntityFactory();
    }

    @Test
    public void autowiredTest() {
        Assert.assertNotNull(otherTestDependencies);
        Assert.assertNotNull(otherTestDependencies.getDecisionSystem());
    }

    @Test
    public void decisionSystemIsLoanGivenGoodClientDataTest() {
        DecisionSystem decisionSystem = otherTestDependencies.getDecisionSystem();
        ClientDataWrapper clientDataWrapper = TestUtil.getClientDataWrapperForLoan();
        Assert.assertTrue(decisionSystem.isLoanGiven(clientDataWrapper));
    }

    @Test
    public void decisionSystemIsLoanGivenBadClientDataTest() {
        DecisionSystem decisionSystem = otherTestDependencies.getDecisionSystem();
        ClientDataWrapper clientDataWrapper = TestUtil.getClientDataWrapperForLoan();

        List<ProductTypeSetting> productTypeSettings = otherTestDependencies.getProductTypeSettingDAO().findByExample(
                entityFactory.getProductTypeSetting(null, 1, null, null));

        int maxAmount = Integer.parseInt(FilterUtil.findProductTypeSettingByValue(
                productTypeSettings, SettingTypeUtil.maxAmount).getValue());

        LocalTime rejectionTimeMax = DateTimeUtil.getLocalTime(FilterUtil.findProductTypeSettingByValue(
                productTypeSettings, SettingTypeUtil.maxRejectionHour).getValue());

        LocalTime rejectionTime = LocalTime.of(rejectionTimeMax.getHour() -1, 0, 0);

        clientDataWrapper.setAmount(maxAmount);
        clientDataWrapper.setApplicationDate(DateTimeUtil.getLocalDateTimeWithLocalTime(
                clientDataWrapper.getApplicationDate(), rejectionTime));
        Assert.assertFalse(decisionSystem.isLoanGiven(clientDataWrapper));
    }
}
