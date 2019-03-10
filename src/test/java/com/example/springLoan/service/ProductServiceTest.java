package com.example.springLoan.service;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.util.constant.EntityUtil;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest extends AbstractTest {

    private final static Integer CLIENT_DATA_WRAPPER_AMOUNT = 5000;

    ProductServiceImpl productServiceImpl;

    @Mock
    CustomerService customerService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductSettingService productSettingService;

    @Mock
    ProductTypeSettingService productTypeSettingService;

    @Mock
    DecisionSystem decisionSystem;

    @Mock
    ProductTypeService productTypeService;

    @Mock
    AbstractFactory abstractFactory;

    ClientDataWrapper clientDataWrapper;



    @Before
    public void setUp(){
        productServiceImpl = new ProductServiceImpl(productRepository, customerService, productTypeService,
                productSettingService, productTypeSettingService, decisionSystem, abstractFactory);
        clientDataWrapper = ClientDataWrapperFactory.getClientDataWrapper(CLIENT_DATA_WRAPPER_AMOUNT, "1986-04-08 12:30", 15);
    }

    @Test
    public void whenClientDataNotValidReturnOptionalEmpty(){
        when(decisionSystem.isLoanGiven(any())).thenReturn(false);
        Optional<Product> optionalProduct = productServiceImpl.getLoan(clientDataWrapper);
        Assert.assertFalse(optionalProduct .isPresent());
    }

    @Test
    public void whenClientDataValidReturnProduct(){

        //given
        when(decisionSystem.isLoanGiven(any())).thenReturn(true);
        when(customerService.findById(any())).thenReturn(Optional.of(new Customer()));
        when(productTypeService.findById(any())).thenReturn(Optional.of(new ProductType()));
        when(productRepository.save(any())).thenAnswer(
                (Answer<Product>) invocationOnMock -> (Product) invocationOnMock.getArguments()[0]);


        when(productSettingService.saveAll(any())).thenAnswer((Answer<List<ProductSetting>>) invocationOnMock
                -> new ArrayList<> ((HashSet<ProductSetting>) invocationOnMock.getArguments()[0]));
        when(productSettingService.getProductSetting(any(), any(), any(), any())).thenAnswer(
                new Answer<ProductSetting>() {
                    @Override
                    public ProductSetting answer(InvocationOnMock invocationOnMock) throws Throwable {
                        ProductSettingService productSettingService = new ProductSettingServiceImpl();
                        return productSettingService.getProductSetting(invocationOnMock.getArgument(0),
                                invocationOnMock.getArgument(1), invocationOnMock.getArgument(2),
                                invocationOnMock.getArgument(3));
                    }
                });


        //create productTypeSettings object - one that should be simply copied into ProductSetting
        // and another one (with name = "amount") that should be modified
        final String PRODUCT_TYPE_SETTING1_VALUE = "1000";
        ProductTypeSetting productTypeSetting1 = new ProductTypeSetting(1, PRODUCT_TYPE_SETTING1_VALUE,
                new ProductType(), new Setting(){{setIsRuntimeInput(false);}});
        ProductTypeSetting productTypeSetting2 = new ProductTypeSetting(2, Strings.EMPTY, new ProductType(),
                new Setting(){{
                    setIsRuntimeInput(true);
                    setName(EntityUtil.Setting.AMOUNT);
        }});
        List<ProductTypeSetting> productTypeSettings = Arrays.asList(productTypeSetting1, productTypeSetting2);
        when(productTypeSettingService.findByProductType_Id(any())).thenReturn(productTypeSettings);


        //when
        Optional<Product> optionalProduct = productServiceImpl.getLoan(clientDataWrapper);

        //then
        Assert.assertTrue(optionalProduct.isPresent());

        Set<ProductSetting> productSettings = optionalProduct.get().getProductSettings();
        Assert.assertTrue("After successful giving the loan in productSettings there should be a setting with " +
                "value equal to the amount of loan asked", productSettings.stream()
                .anyMatch(x -> x.getValue().equals(CLIENT_DATA_WRAPPER_AMOUNT.toString())));
        Assert.assertTrue(productSettings.stream().anyMatch(x
                -> x.getValue().equals(PRODUCT_TYPE_SETTING1_VALUE)));

    }

}
