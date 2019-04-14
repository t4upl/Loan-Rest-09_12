package com.example.loanrestapi.decisionsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.loanrestapi.AbstractTest;
import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.enums.DataTypeEnum;
import com.example.loanrestapi.enums.SettingName;
import com.example.loanrestapi.model.ProductTypeSetting;
import com.example.loanrestapi.repository.ProductTypeSettingRepository;
import com.example.loanrestapi.service.ProductTypeSettingService;
import com.example.loanrestapi.service.ProductTypeSettingServiceImpl;
import com.example.loanrestapi.util.TestingUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DecisionSystemTest extends AbstractTest {

  private static final String APPLICATION_DATE = "1986-04-08 12:30:00";
  private DecisionSystem decisionSystem;

  private static final String MIN_AMOUNT = "1000";
  private static final String MAX_AMOUNT = "5000";

  private static final String MIN_TERM = "3";
  private static final String MAX_TERM = "100";

  private static final String MIN_REJECTION_TIME = "00:00:00";
  private static final String MAX_REJECTION_TIME = "06:00:00";


  /**
   * SetUp.
   */
  @Before
  public void setUp() {
    ProductTypeSettingRepository productTypeSettingRepository = Mockito
        .mock(ProductTypeSettingRepository.class,
        new NullPointerExceptionAnswer("ProductTypeSettingRepository"));
    ProductTypeSettingService productTypeSettingService = new ProductTypeSettingServiceImpl(
        productTypeSettingRepository);
    DecisionRuleFactory decisionRuleFactory = new DecisionRuleFactoryImpl(
        productTypeSettingService);
    this.decisionSystem = new DecisionSystemImpl(decisionRuleFactory);

    doAnswer(i -> getProductTypeSettingListForMock()).when(productTypeSettingRepository)
      .findByProductType_Id(any());
  }

  @Test
  public void whenProperClientDataReturnTrue() {
    Assert.assertTrue(decisionSystem.isLoanGiven(getProductRequestDto(2000,
        APPLICATION_DATE, 15)));
  }

  @Test
  public void whenAmountEqualsMaxAmountReturnTrue() {
    Assert.assertTrue(decisionSystem.isLoanGiven(getProductRequestDto(5000,
        APPLICATION_DATE, 15)));
  }

  @Test
  public void whenAmountBiggerThanMaxAmountReturnFalse() {
    Assert.assertFalse(decisionSystem.isLoanGiven(getProductRequestDto(20000000,
        APPLICATION_DATE, 15)));
  }

  @Test
  public void whenAmountSamllerThanMinAmountReturnFalse() {
    Assert.assertFalse(decisionSystem.isLoanGiven(getProductRequestDto(-1,
        APPLICATION_DATE, 15)));
  }

  @Test
  public void whenTermBiggerThanMaxTermReturnFalse() {
    Assert.assertFalse(decisionSystem.isLoanGiven(getProductRequestDto(2000,
        APPLICATION_DATE, 15000)));
  }

  @Test
  public void whenAmountEqualsMaxAndApplicationHourBetweenMinAndMaxAmountReturnFalse() {
    Assert.assertFalse(decisionSystem.isLoanGiven(getProductRequestDto(5000,
        "1986-04-08 05:30:00", 15)));
  }

  @Test
  public void whenAmountSmallerThanMaxAndApplicationHourBetweenMinAndMaxAmountReturnTrue() {
    Assert.assertTrue(decisionSystem.isLoanGiven(getProductRequestDto(4900,
        "1986-04-08 05:30:00", 15)));
  }

  private ProductRequestDto getProductRequestDto(Integer amount, String applicationDate,
      Integer term) {
    return TestingUtil.getProductRequestDto(amount, applicationDate, term);
  }

  private ProductTypeSetting getProductTypeSetting(String settingName, String dataTypeName,
      String value) {
    ProductTypeSetting productTypeSetting = mock(ProductTypeSetting.class,
        Mockito.RETURNS_DEEP_STUBS);
    when(productTypeSetting.getSetting().getName()).thenReturn(SettingName.valueOf(settingName));
    when(productTypeSetting.getSetting().getDataType().getName())
      .thenReturn(DataTypeEnum.valueOf(dataTypeName));
    when(productTypeSetting.getValue()).thenReturn(value);
    return productTypeSetting;
  }

  private List<ProductTypeSetting> getProductTypeSettingListForMock() {
    List<ProductTypeSetting> productTypeSettings = new ArrayList<>();

    productTypeSettings
        .add(getProductTypeSetting(SettingName.MIN_AMOUNT.toString(),
          DataTypeEnum.INTEGER.toString(), MIN_AMOUNT));
    productTypeSettings
        .add(getProductTypeSetting(SettingName.MAX_AMOUNT.toString(),
          DataTypeEnum.INTEGER.toString(), MAX_AMOUNT));

    productTypeSettings
        .add(getProductTypeSetting(SettingName.MIN_TERM.toString(), DataTypeEnum.INTEGER.toString(),
        MIN_TERM));
    productTypeSettings
        .add(getProductTypeSetting(SettingName.MAX_TERM.toString(), DataTypeEnum.INTEGER.toString(),
        MAX_TERM));

    productTypeSettings.add(getProductTypeSetting(SettingName.MIN_REJECTION_TIME.toString(),
        DataTypeEnum.LOCAL_TIME.toString(), MIN_REJECTION_TIME));
    productTypeSettings.add(getProductTypeSetting(SettingName.MAX_REJECTION_TIME.toString(),
        DataTypeEnum.LOCAL_TIME.toString(), MAX_REJECTION_TIME));

    return productTypeSettings;
  }
}
