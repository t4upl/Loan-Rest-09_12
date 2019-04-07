package com.example.springLoan.service;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.enums.DataTypeEnum;
import com.example.springLoan.enums.SettingName;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.DataType;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.model.Setting;
import com.example.springLoan.repository.ProductSettingRepository;
import com.example.springLoan.util.FilterUtil;
import com.example.springLoan.util.TestingUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ProductSettingServiceTest extends AbstractTest {

    public static final String APPLICATION_DATE = "1986-04-08 12:30:00";
    private final static Integer CLIENT_DATA_WRAPPER_AMOUNT = 5000;
    public static final int TERM = 15;

    private ProductSettingService productSettingService;
    private ProductSettingRepository productSettingRepository;
    private ProductTypeSettingService productTypeSettingService;
    private AbstractFactory abstractFactory;

    private ProductRequestDTO productRequestDTO;

    @Before
    public void setUp(){
        this.productSettingRepository = Mockito.mock(ProductSettingRepository.class,
                new NullPointerExceptionAnswer("productSettingRepository"));
        this.productTypeSettingService = Mockito.mock(ProductTypeSettingService.class,
                new NullPointerExceptionAnswer("productTypeSettingService"));
        this.abstractFactory = Mockito.mock(AbstractFactory.class, Mockito.RETURNS_DEEP_STUBS);

        this.productSettingService = new ProductSettingServiceImpl(productSettingRepository, productTypeSettingService,
                abstractFactory);

        this.productRequestDTO = TestingUtil.getProductRequestDTO(CLIENT_DATA_WRAPPER_AMOUNT,
                APPLICATION_DATE, TERM);
    }

    @Test
    public void whenClientDataValidReturnProductSettingSet(){
        //given
        final String PRODUCT_TYPE_SETTING_VALUE = "1000";
        final String TERM_SETTING = "30";

        Setting setting1 = new Setting(null, SettingName.AMOUNT,
                new DataType(null, DataTypeEnum.INTEGER, null),
                true, null, null);
        ProductTypeSetting productTypeSetting1 = new ProductTypeSetting(null, PRODUCT_TYPE_SETTING_VALUE,
                null, setting1);

        Setting setting2 = new Setting(null, SettingName.TERM,
                new DataType(null, DataTypeEnum.INTEGER, null),
                false, null, null);
        ProductTypeSetting productTypeSetting2 = new ProductTypeSetting(null, TERM_SETTING, null,
                setting2);
        List<ProductTypeSetting> productTypeSettingsMock = Arrays.asList(productTypeSetting1, productTypeSetting2);

        doReturn(productTypeSettingsMock).when(productTypeSettingService).findByProductType_Id(any());
        when(abstractFactory.getProductSettingFactory().getProductSetting(any(), anyString(), any(), any(Setting.class)))
                .thenAnswer((Answer<ProductSetting>) inv ->
                        new ProductSetting(null, inv.getArgument(1), null, inv.getArgument(3)));

        //when
        Set<ProductSetting> productSettings = productSettingService.getProductSettings(productRequestDTO);

        //then
        Assert.assertEquals("productSetting size should be the same as productTypeSettingsMock size",
                productTypeSettingsMock.size(), productSettings.size());

        Assert.assertTrue("productSetting should contain element wiht name equal to 'amount'.",
                productSettings.stream().anyMatch(x -> SettingName.AMOUNT.equals(x.getSetting().getName())));

        Assert.assertTrue("productSetting should contain element wiht name equal to 'term'.",
                productSettings.stream().anyMatch(x -> SettingName.TERM.equals(x.getSetting().getName())));

        Assert.assertEquals("productSetting with isRuntimeInput set to true " +
                        "should have value based on productRequestDTO.",
                CLIENT_DATA_WRAPPER_AMOUNT.toString(), getValueByName(productSettings, SettingName.AMOUNT.toString()));

        Assert.assertEquals("productSetting with isRuntimeInput set to false " +
                        "should have value base don ProductTypeSetting.",
                TERM_SETTING, getValueByName(productSettings, SettingName.TERM.toString()));
    }

    @Test
    public void whenExtendLoanReturnChangeProductSettingWithNameDueDate(){
        String localDateString = "2018-11-07 15:12:54";
        String termString = "3";
        String foobarString = "5";
        String foobarName = "foobar";

        LocalDateTime localDateTime = (LocalDateTime) FilterUtil.convertStringToJava(
                localDateString, DataTypeEnum.LOCAL_DATE_TIME.toString());
        Integer term = (Integer) FilterUtil.convertStringToJava(
                termString, DataTypeEnum.INTEGER.toString());
        Set<ProductSetting> productSettingsMock = new HashSet<>();
        productSettingsMock.add(getProductSetting(DataTypeEnum.LOCAL_DATE_TIME.toString(), SettingName.DUE_DATE.toString(),
                localDateString));
        productSettingsMock.add(getProductSetting(DataTypeEnum.INTEGER.toString(), SettingName.EXTENSION_TERM.toString(),
                termString));
        productSettingsMock.add(getProductSetting(DataTypeEnum.INTEGER.toString(), foobarName, foobarString));

        Set<ProductSetting> productSettings = productSettingService.addExtensionTermToDueDate(productSettingsMock);

        Optional<ProductSetting> dueDateOpt = getProductSettingBySettingName(productSettings,
                SettingName.DUE_DATE.toString());
        Optional<ProductSetting> termOpt = getProductSettingBySettingName(productSettings,
                SettingName.EXTENSION_TERM.toString());
        Optional<ProductSetting> foobarOpt = getProductSettingBySettingName(productSettings, foobarName);
        Assert.assertTrue("ProductSettings should contain ProductSetting with name 'due date'",
                dueDateOpt.isPresent());
        Assert.assertTrue("ProductSettings should contain ProductSetting with name 'term'",
                termOpt.isPresent());
        Assert.assertTrue("ProductSettings should contain ProductSetting with name 'foobar'",
                foobarOpt.isPresent());

        Assert.assertEquals("ProductSetting with name 'due date' should have been extended by term",
                FilterUtil.convertJavaToString(localDateTime.plusDays(term), DataTypeEnum.LOCAL_DATE_TIME.toString()),
                dueDateOpt.get().getValue());
        Assert.assertEquals("ProductSetting with name 'term' should not change",
                termString, termOpt.get().getValue());
        Assert.assertEquals("ProductSetting with name 'foobar' should not change",
                foobarString, foobarOpt.get().getValue());
    }

    private Optional<ProductSetting> getProductSettingBySettingName (Set<ProductSetting> productSettings,
                                                                     String setingName){
        return productSettings.stream().filter(x-> setingName.equals(x.getSetting().getName().toString())).findFirst();
    }

    private ProductSetting getProductSetting(String dataTypeName, String settingName, String productSettingValue){
        DataType dataType = new DataType(null, DataTypeEnum.valueOf(dataTypeName), null);
        Setting setting = new Setting(null, SettingName.valueOf(settingName), dataType , null,
                null, null);
        return new ProductSetting(1L, productSettingValue, null, setting);
    }

    private String getValueByName(Set<ProductSetting> productSettings, String name){
        return productSettings
                .stream()
                .filter(x -> name.equals(x.getSetting().getName().toString()))
                .findFirst()
                .get()
                .getValue();
    }
}
