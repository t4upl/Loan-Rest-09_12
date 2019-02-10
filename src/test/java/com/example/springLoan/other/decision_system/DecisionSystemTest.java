package com.example.springLoan.other.decision_system;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.ProductTypeSettingRepository;
import com.example.springLoan.util.TestingUtil;
import com.example.springLoan.util.constant.ApplicationConstant;
import com.example.springLoan.util.constant.EntityUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DecisionSystemTest extends AbstractTest {

    @Autowired
    DecisionSystemImpl decisionSystemImpl;

    @MockBean
    ProductTypeSettingRepository productTypeSettingRepository;

    private static final String MIN_AMOUNT = "1000";
    private static final String MAX_AMOUNT = "5000";

    private static final String MIN_TERM = "3";
    private static final String MAX_TERM = "100";

    private static final String MIN_REJECTION_TIME = "00:00:00";
    private static final String MAX_REJECTION_TIME = "06:00:00";


    @Before
    public void setUp() {
        when(productTypeSettingRepository.findByProductType_Id(any()))
                .thenAnswer(i -> getProductTypeSettingListForMock());
    }

    @Test
    public void whenProperClientDataReturnTrue() {
        Assert.assertTrue(decisionSystemImpl.isLoanGiven(getClientDataWrapper(2000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountEqualsMaxAmountReturnTrue() {
        Assert.assertTrue(decisionSystemImpl.isLoanGiven(getClientDataWrapper(5000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountBiggerThanMaxAmountReturnFalse() {
        Assert.assertFalse(decisionSystemImpl.isLoanGiven(getClientDataWrapper(20000000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountSamllerThanMinAmountReturnFalse() {
        Assert.assertFalse(decisionSystemImpl.isLoanGiven(getClientDataWrapper(-1,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenTermBiggerThanMaxTermReturnFalse() {
        Assert.assertFalse(decisionSystemImpl.isLoanGiven(getClientDataWrapper(2000,
                "1986-04-08 12:30", 15000)));
    }

    @Test
    public void whenAmountEqualsMaxAndApplicationHourBetweenMinAndMaxAmountReturnFalse() {
        Assert.assertFalse(decisionSystemImpl.isLoanGiven(getClientDataWrapper(5000,
                "1986-04-08 05:30", 15)));
    }

    @Test
    public void whenAmountSmallerThanMaxAndApplicationHourBetweenMinAndMaxAmountReturnTrue() {
        Assert.assertTrue(decisionSystemImpl.isLoanGiven(getClientDataWrapper(4900,
                "1986-04-08 05:30", 15)));
    }

    private ClientDataWrapper getClientDataWrapper(Integer amount, String applicationDate, Integer term) {
        return ClientDataWrapperFactory.getClientDataWrapper(amount, applicationDate, term);
    }

    private ProductTypeSetting getProductTypeSetting(String settingName, String dataTypeName, String value) {
        ProductTypeSetting productTypeSetting = mock(ProductTypeSetting.class, Mockito.RETURNS_DEEP_STUBS);
        when(productTypeSetting.getSetting().getName()).thenReturn(settingName);
        when(productTypeSetting.getSetting().getDataType().getName()).thenReturn(dataTypeName);
        when(productTypeSetting.getValue()).thenReturn(value);
        return productTypeSetting;
    }

    public List<ProductTypeSetting> getProductTypeSettingListForMock(){
        List<ProductTypeSetting> productTypeSettings = new ArrayList<>();

        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MIN_AMOUNT, EntityUtil.DataType.INTEGER,
                MIN_AMOUNT));
        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MAX_AMOUNT, EntityUtil.DataType.INTEGER,
                MAX_AMOUNT));

        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MIN_TERM, EntityUtil.DataType.INTEGER,
                MIN_TERM));
        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MAX_TERM, EntityUtil.DataType.INTEGER,
                MAX_TERM));

        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MIN_REJECTION_TIME,
                EntityUtil.DataType.LOCAL_TIME, MIN_REJECTION_TIME));
        productTypeSettings.add(getProductTypeSetting(EntityUtil.Setting.MAX_REJECTION_TIME,
                EntityUtil.DataType.LOCAL_TIME, MAX_REJECTION_TIME));

        return productTypeSettings;
    }


}
