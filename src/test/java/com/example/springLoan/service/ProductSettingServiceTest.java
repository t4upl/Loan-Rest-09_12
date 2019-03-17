package com.example.springLoan.service;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.ProductSettingRepository;
import com.example.springLoan.util.constant.EntityUtil;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ProductSettingServiceTest extends AbstractTest {

    public static final String APPLICATION_DATE = "1986-04-08 12:30";
    private final static Integer CLIENT_DATA_WRAPPER_AMOUNT = 5000;
    public static final int TERM = 15;

    ProductSettingService productSettingService;
    ProductSettingRepository productSettingRepository;
    ProductTypeSettingService productTypeSettingService;
    AbstractFactory abstractFactory;

    ClientDataWrapper clientDataWrapper;

    @Before
    public void setUp(){
        this.productSettingRepository = Mockito.mock(ProductSettingRepository.class,
                new NullPointerExceptionAnswer("productSettingRepository"));
        this.productTypeSettingService = Mockito.mock(ProductTypeSettingService.class,
                new NullPointerExceptionAnswer("productTypeSettingService"));
        this.abstractFactory = Mockito.mock(AbstractFactory.class, Mockito.RETURNS_DEEP_STUBS);

        this.productSettingService = new ProductSettingServiceImpl(productSettingRepository, productTypeSettingService,
                abstractFactory);

        this.clientDataWrapper = ClientDataWrapperFactory.getClientDataWrapper(CLIENT_DATA_WRAPPER_AMOUNT,
                APPLICATION_DATE, TERM);
    }

    @Test
    public void whenClientDataValidReturnProductSettingSet(){
        //given
        final String PRODUCT_TYPE_SETTING_VALUE = "1000";
        final String TERM_SETTING = "30";

        Setting setting1 = new Setting(null, EntityUtil.Setting.AMOUNT,
                new DataType(null, EntityUtil.DataType.INTEGER, null),
                true, null, null);
        ProductTypeSetting productTypeSetting1 = new ProductTypeSetting(null, PRODUCT_TYPE_SETTING_VALUE,
                null, setting1);

        Setting setting2 = new Setting(null, EntityUtil.Setting.TERM,
                new DataType(null, EntityUtil.DataType.INTEGER, null),
                false, null, null);
        ProductTypeSetting productTypeSetting2 = new ProductTypeSetting(null, TERM_SETTING, null,
                setting2);
        List<ProductTypeSetting> productTypeSettingsMock = Arrays.asList(productTypeSetting1, productTypeSetting2);

        doReturn(productTypeSettingsMock).when(productTypeSettingService).findByProductType_Id(any());
        when(abstractFactory.getProductSettingFactory().getProductSetting(any(), anyString(), any(), any(Setting.class)))
                .thenAnswer((Answer<ProductSetting>) inv ->
                        new ProductSetting(null, inv.getArgument(1), null, inv.getArgument(3)));

        //when
        Set<ProductSetting> productSettings = productSettingService.getProductSettings(clientDataWrapper);

        //then
        Assert.assertEquals("productSetting size should be the same as productTypeSettingsMock size",
                productTypeSettingsMock.size(), productSettings.size());

        Assert.assertTrue("productSetting should contain element wiht name equal to 'amount'.",
                productSettings.stream().anyMatch(x -> EntityUtil.Setting.AMOUNT.equals(x.getSetting().getName())));

        Assert.assertTrue("productSetting should contain element wiht name equal to 'term'.",
                productSettings.stream().anyMatch(x -> EntityUtil.Setting.TERM.equals(x.getSetting().getName())));

        Assert.assertEquals("productSetting with isRuntimeInput set to true " +
                        "should have value based on clientDataWrapper.",
                CLIENT_DATA_WRAPPER_AMOUNT.toString(), getValueByName(productSettings, EntityUtil.Setting.AMOUNT));

        Assert.assertEquals("productSetting with isRuntimeInput set to false " +
                        "should have value base don ProductTypeSetting.",
                TERM_SETTING, getValueByName(productSettings, EntityUtil.Setting.TERM));
    }

    private String getValueByName(Set<ProductSetting> productSettings, String name){
        return productSettings
                .stream()
                .filter(x -> name.equals(x.getSetting().getName()))
                .findFirst()
                .get()
                .getValue();
    }
}
