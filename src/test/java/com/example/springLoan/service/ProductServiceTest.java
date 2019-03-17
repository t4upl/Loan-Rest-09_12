package com.example.springLoan.service;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.factory.AbstractFactoryImpl;
import com.example.springLoan.factory.ProductFactory;
import com.example.springLoan.factory.ProductSettingFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.util.constant.EntityUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest extends AbstractTest {

    private final static Integer CLIENT_DATA_WRAPPER_AMOUNT = 5000;

    ProductService productService;

    //Mocks
    CustomerService customerService;

//    @Mock
    ProductRepository productRepository;

//    @Mock
    ProductSettingService productSettingService;

//    @Mock
    ProductTypeSettingService productTypeSettingService;

//    @Mock
    DecisionSystem decisionSystem;

//    @Mock
    ProductTypeService productTypeService;

//    @Mock
    AbstractFactory abstractFactory;

    //Local variables
    ClientDataWrapper clientDataWrapper;


    @Before
    public void setUp(){
        this.productRepository = Mockito.mock(ProductRepository.class, new NullPointerExceptionAnswer("productRepository"));
        this.customerService = Mockito.mock(CustomerService.class, new NullPointerExceptionAnswer("customerService"));
        this.productTypeService = Mockito.mock(ProductTypeService.class,
                new NullPointerExceptionAnswer("productTypeService"));
        this.productSettingService = Mockito.mock(ProductSettingService.class,
                new NullPointerExceptionAnswer("productSettingService"));
        this.productTypeSettingService = Mockito.mock(ProductTypeSettingService.class,
                new NullPointerExceptionAnswer("productTypeSettingService "));
        this.decisionSystem = Mockito.mock(DecisionSystem.class, new NullPointerExceptionAnswer("decisionSystem"));
        this.abstractFactory = Mockito.mock(AbstractFactory.class, Mockito.RETURNS_DEEP_STUBS);


        this.productService = new ProductServiceImpl(productRepository, customerService, productTypeService,
                productSettingService, productTypeSettingService, decisionSystem, abstractFactory);

        this.clientDataWrapper = ClientDataWrapperFactory.getClientDataWrapper(CLIENT_DATA_WRAPPER_AMOUNT, "1986-04-08 12:30", 15);
    }

    @Test
    public void whenClientDataNotValidReturnOptionalEmpty(){
        when(decisionSystem.isLoanGiven(any())).thenReturn(false);
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);
        Assert.assertFalse(optionalProduct .isPresent());
    }

    @Test
    public void whenClientDataValidReturnProduct_2(){
        //given
        doReturn(true).when(decisionSystem).isLoanGiven(any());
        doReturn(Optional.of(new Customer())).when(customerService).findById(any());
        doReturn(Optional.of(new ProductType())).when(productTypeService).findById(any());

        ProductSetting productSetting = new ProductSetting();
        Set<ProductSetting> productSettingsMock = new HashSet<>();
        productSettingsMock.add(productSetting);


        doAnswer((Answer<Product>) invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            product.setProductSettings(productSettingsMock);
            return product;
        }).when(productRepository).save(any());
        when(abstractFactory.getProductFactory().getProduct(any(), any(), any(), any()))
                .thenAnswer((Answer<Product>) inv -> new Product(inv.getArgument(0), inv.getArgument(1),
                        inv.getArgument(2), inv.getArgument(3)));

        doReturn(productSettingsMock).when(productSettingService).getProductSettings(any());

        doAnswer((Answer<List<ProductSetting>>) invocationOnMock ->
                new ArrayList<>((Set<ProductSetting>)invocationOnMock.getArguments()[0]))
                .when(productSettingService).saveAll(any());

        //when
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);

        //then
        Assert.assertTrue(optionalProduct.isPresent());

        Product product = optionalProduct.get();
        Set<ProductSetting> productSettings = product.getProductSettings();
        Assert.assertEquals("There should be 1 element in productSettings list",1,
                productSettings.size());
        }

    @Test
    public void whenClientDataValidReturnProduct(){
        //given
        doReturn(true).when(decisionSystem).isLoanGiven(any());
        doReturn(Optional.of(new Customer())).when(customerService).findById(any());
        doReturn(Optional.of(new ProductType())).when(productTypeService).findById(any());
        when(abstractFactory.getProductFactory().getProduct(any(), any(), any(), any()))
            .thenAnswer((Answer<Product>) inv -> new Product(inv.getArgument(0), inv.getArgument(1),
                    inv.getArgument(2), inv.getArgument(3)));


        doAnswer((Answer<List<ProductSetting>>) invocationOnMock
                -> new ArrayList<> ((HashSet<ProductSetting>) invocationOnMock.getArguments()[0]))
                .when(productSettingService).saveAll(any());

        doAnswer((Answer<Product>) invocationOnMock -> (Product) invocationOnMock.getArguments()[0])
                .when(productRepository).save(any());

        //create productTypeSettings object - one that should be simply copied into ProductSetting
        // and another one (with name = "amount") that should be modified
//        final String PRODUCT_TYPE_SETTING1_VALUE = "1000";
//        ProductTypeSetting productTypeSetting1 = new ProductTypeSetting(1, PRODUCT_TYPE_SETTING1_VALUE,
//                new ProductType(), new Setting(){{setIsRuntimeInput(false);}});
//        ProductTypeSetting productTypeSetting2 = new ProductTypeSetting(2, Strings.EMPTY, new ProductType(),
//                new Setting(){{
//                    setIsRuntimeInput(true);
//                    setName(EntityUtil.Setting.AMOUNT);
//        }});
//        List<ProductTypeSetting> productTypeSettings = Arrays.asList(productTypeSetting1, productTypeSetting2);

//        doReturn(productTypeSettings).when(productTypeSettingService).findByProductType_Id(any());
//        when(abstractFactory.getProductSettingFactory().getProductSetting(any(), any(), any(), any()))
//                .thenAnswer((Answer<ProductSetting>) inv -> new ProductSetting(inv.getArgument(0),
//                        inv.getArgument(1), inv.getArgument(2), inv.getArgument(3)));

        //when
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);

        //then
        Assert.assertTrue(optionalProduct.isPresent());

        Set<ProductSetting> productSettings = optionalProduct.get().getProductSettings();
        Assert.assertEquals("There should be 2 productTypeSettings present in list",2, productSettings.size());

//        Assert.assertTrue("After successful giving the loan in productSettings there should be a setting with " +
//                "value equal to the amount of loan asked", productSettings.stream()
//                .anyMatch(x -> x.getValue().equals(CLIENT_DATA_WRAPPER_AMOUNT.toString())));
//        Assert.assertTrue(productSettings.stream().anyMatch(x
//                -> x.getValue().equals(PRODUCT_TYPE_SETTING1_VALUE)));

    }



    //stric mocking class
    @AllArgsConstructor
    @NoArgsConstructor
    class NullPointerExceptionAnswer<T> implements Answer<T> {
        private static final String STRICT_MOCKING_ERROR = "Strict mocking error: ";
        private String message = "no message provided.";


        @Override
        public T answer(InvocationOnMock invocation) throws Throwable {
            throw new NullPointerException(STRICT_MOCKING_ERROR + this.message);
        }
    }

}
