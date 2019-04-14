package com.example.loanrestapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.example.loanrestapi.AbstractTest;
import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.enums.DataTypeEnum;
import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.factory.AbstractFactory;
import com.example.loanrestapi.model.DataType;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductTypeSetting;
import com.example.loanrestapi.model.Setting;
import com.example.loanrestapi.repository.ProductSettingRepository;
import com.example.loanrestapi.util.FilterUtil;
import com.example.loanrestapi.util.TestingUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class ProductSettingServiceTest extends AbstractTest {

  private static final String APPLICATION_DATE = "1986-04-08 12:30:00";
  private static final Integer CLIENT_DATA_WRAPPER_AMOUNT = 5000;
  private static final int TERM = 15;

  private ProductSettingService productSettingService;
  private ProductTypeSettingService productTypeSettingService;
  private AbstractFactory abstractFactory;

  private ProductRequestDto productRequestDto;

  /**
   * SetUp.
   */
  @Before
  public void setUp() {
    ProductSettingRepository productSettingRepository = Mockito
        .mock(ProductSettingRepository.class,
        new NullPointerExceptionAnswer("productSettingRepository"));
    this.productTypeSettingService = Mockito.mock(ProductTypeSettingService.class,
      new NullPointerExceptionAnswer("productTypeSettingService"));
    this.abstractFactory = Mockito.mock(AbstractFactory.class, Mockito.RETURNS_DEEP_STUBS);

    this.productSettingService = new ProductSettingServiceImpl(productSettingRepository,
      productTypeSettingService,
      abstractFactory);

    this.productRequestDto = TestingUtil.getProductRequestDto(CLIENT_DATA_WRAPPER_AMOUNT,
      APPLICATION_DATE, TERM);
  }

  @Test
  public void whenClientDataValidReturnProductSettingSet() {
    //given
    final String productTypeSettingValue = "1000";
    final String termSetting = "30";

    Setting setting1 = new Setting(null, SettingName.AMOUNT,
        new DataType(null, DataTypeEnum.INTEGER, null),
        true, null, null);
    ProductTypeSetting productTypeSetting1 = new ProductTypeSetting(null,
        productTypeSettingValue, null, setting1);

    Setting setting2 = new Setting(null, SettingName.TERM,
        new DataType(null, DataTypeEnum.INTEGER, null),
        false, null, null);
    ProductTypeSetting productTypeSetting2 = new ProductTypeSetting(null, termSetting, null,
        setting2);
    List<ProductTypeSetting> productTypeSettingsMock = Arrays
        .asList(productTypeSetting1, productTypeSetting2);

    doReturn(productTypeSettingsMock).when(productTypeSettingService).findByProductType_Id(any());
    when(abstractFactory.getProductSettingFactory()
        .getProductSetting(any(), anyString(), any(), any(Setting.class)))
        .thenAnswer((Answer<ProductSetting>) inv ->
          new ProductSetting(null, inv.getArgument(1), null, inv.getArgument(3)));

    //when
    Set<ProductSetting> productSettings = productSettingService.getProductSettings(
        productRequestDto);

    //then
    Assert.assertEquals("productSetting size should be the same as productTypeSettingsMock"
        + " size", productTypeSettingsMock.size(), productSettings.size());

    Assert.assertTrue("productSetting should contain element wiht name equal to 'amount'.",
          productSettings.stream().anyMatch(x ->
              SettingName.AMOUNT.equals(x.getSetting().getName())));

    Assert.assertTrue("productSetting should contain element wiht name equal to 'term'.",
        productSettings.stream().anyMatch(x ->
            SettingName.TERM.equals(x.getSetting().getName())));

    Assert.assertEquals("productSetting with isRuntimeInput set to true"
        + " should have value based on productRequestDto.",
        CLIENT_DATA_WRAPPER_AMOUNT.toString(), getValueByName(productSettings,
        SettingName.AMOUNT.toString()));

    Assert.assertEquals("productSetting with isRuntimeInput set to false"
        + " should have value base don ProductTypeSetting.", termSetting,
        getValueByName(productSettings, SettingName.TERM.toString()));
  }

  @Test
  public void whenExtendLoanReturnChangeProductSettingWithNameDueDate() {
    String localDateString = "2018-11-07 15:12:54";
    String termString = "3";
    String foobarString = "5";
    SettingName foobarName = SettingName.MAX_REJECTION_TIME;

    Set<ProductSetting> productSettingsMock = new HashSet<>();
    productSettingsMock
        .add(getProductSetting(DataTypeEnum.LOCAL_DATE_TIME.toString(), SettingName.DUE_DATE,
        localDateString));
    productSettingsMock
        .add(getProductSetting(DataTypeEnum.INTEGER.toString(), SettingName.EXTENSION_TERM,
        termString));
    productSettingsMock
      .add(getProductSetting(DataTypeEnum.INTEGER.toString(), foobarName, foobarString));

    Set<ProductSetting> productSettings = productSettingService
        .addExtensionTermToDueDate(productSettingsMock);

    Optional<ProductSetting> dueDateOpt = getProductSettingBySettingName(productSettings,
        SettingName.DUE_DATE);
    Optional<ProductSetting> termOpt = getProductSettingBySettingName(productSettings,
        SettingName.EXTENSION_TERM);
    Optional<ProductSetting> foobarOpt = getProductSettingBySettingName(productSettings,
        foobarName);
    Assert.assertTrue("ProductSettings should contain ProductSetting with name 'due date'",
        dueDateOpt.isPresent());
    Assert.assertTrue("ProductSettings should contain ProductSetting with name 'term'",
        termOpt.isPresent());
    Assert.assertTrue("ProductSettings should contain ProductSetting with name 'foobar'",
        foobarOpt.isPresent());


    LocalDateTime localDateTime = (LocalDateTime) FilterUtil.convertStringToJava(
        localDateString, DataTypeEnum.LOCAL_DATE_TIME.toString());

    Integer term = (Integer) FilterUtil.convertStringToJava(
        termString, DataTypeEnum.INTEGER.toString());

    Assert.assertEquals("ProductSetting with name 'due date' should have been extended by term",
        FilterUtil.convertJavaToString(localDateTime.plusDays(term),
          DataTypeEnum.LOCAL_DATE_TIME.toString()), dueDateOpt.get().getValue());
    Assert.assertEquals("ProductSetting with name 'term' should not change",
        termString, termOpt.get().getValue());
    Assert.assertEquals("ProductSetting with name 'foobar' should not change",
        foobarString, foobarOpt.get().getValue());
  }

  private Optional<ProductSetting> getProductSettingBySettingName(
      Set<ProductSetting> productSettings,
      SettingName settingName) {
    return productSettings.stream().filter(x -> settingName.equals(x.getSetting().getName()))
      .findFirst();
  }

  private ProductSetting getProductSetting(String dataTypeName, SettingName settingName,
      String productSettingValue) {
    DataType dataType = new DataType(null, DataTypeEnum.valueOf(dataTypeName), null);
    Setting setting = new Setting(null, settingName, dataType, null,
        null, null);
    return new ProductSetting(1L, productSettingValue, null, setting);
  }

  private String getValueByName(Set<ProductSetting> productSettings, String name) {
    return productSettings
      .stream()
      .filter(x -> name.equals(x.getSetting().getName().toString()))
      .findFirst()
      .get()
      .getValue();
  }
}
