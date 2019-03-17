package com.example.springLoan.decision_system;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.ProductTypeSettingRepository;
import com.example.springLoan.service.ProductTypeSettingService;
import com.example.springLoan.service.ProductTypeSettingServiceImpl;
import com.example.springLoan.util.constant.EntityUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DecisionSystemTest extends AbstractTest {

    DecisionSystem decisionSystem;
    DecisionRuleFactory decisionRuleFactory;
    ProductTypeSettingService productTypeSettingService;
    ProductTypeSettingRepository productTypeSettingRepository;

    private static final String MIN_AMOUNT = "1000";
    private static final String MAX_AMOUNT = "5000";

    private static final String MIN_TERM = "3";
    private static final String MAX_TERM = "100";

    private static final String MIN_REJECTION_TIME = "00:00:00";
    private static final String MAX_REJECTION_TIME = "06:00:00";


    @Before
    public void setUp() {
        this.productTypeSettingRepository = Mockito.mock(ProductTypeSettingRepository.class,
                new NullPointerExceptionAnswer("ProductTypeSettingRepository"));
        this.productTypeSettingService = new ProductTypeSettingServiceImpl(productTypeSettingRepository);
        this.decisionRuleFactory = new DecisionRuleFactoryImpl(productTypeSettingService);
        this.decisionSystem = new DecisionSystemImpl(decisionRuleFactory);

        doAnswer(i -> getProductTypeSettingListForMock()).when(productTypeSettingRepository)
                .findByProductType_Id(any());
    }

    @Test
    public void whenProperClientDataReturnTrue() {
        Assert.assertTrue(decisionSystem.isLoanGiven(getClientDataWrapper(2000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountEqualsMaxAmountReturnTrue() {
        Assert.assertTrue(decisionSystem.isLoanGiven(getClientDataWrapper(5000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountBiggerThanMaxAmountReturnFalse() {
        Assert.assertFalse(decisionSystem.isLoanGiven(getClientDataWrapper(20000000,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenAmountSamllerThanMinAmountReturnFalse() {
        Assert.assertFalse(decisionSystem.isLoanGiven(getClientDataWrapper(-1,
                "1986-04-08 12:30", 15)));
    }

    @Test
    public void whenTermBiggerThanMaxTermReturnFalse() {
        Assert.assertFalse(decisionSystem.isLoanGiven(getClientDataWrapper(2000,
                "1986-04-08 12:30", 15000)));
    }

    @Test
    public void whenAmountEqualsMaxAndApplicationHourBetweenMinAndMaxAmountReturnFalse() {
        Assert.assertFalse(decisionSystem.isLoanGiven(getClientDataWrapper(5000,
                "1986-04-08 05:30", 15)));
    }

    @Test
    public void whenAmountSmallerThanMaxAndApplicationHourBetweenMinAndMaxAmountReturnTrue() {
        Assert.assertTrue(decisionSystem.isLoanGiven(getClientDataWrapper(4900,
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

    private List<ProductTypeSetting> getProductTypeSettingListForMock(){
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
