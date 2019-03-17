package com.example.springLoan.service;

import com.example.springLoan.AbstractTest;
import com.example.springLoan.ClientDataWrapperFactory;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductType;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest extends AbstractTest {
    ProductService productService;
    CustomerService customerService;
    ProductRepository productRepository;
    ProductSettingService productSettingService;
    ProductTypeSettingService productTypeSettingService;
    DecisionSystem decisionSystem;
    ProductTypeService productTypeService;
    AbstractFactory abstractFactory;

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

        this.clientDataWrapper = ClientDataWrapperFactory.getClientDataWrapper(null, "1986-04-08 12:30", 15);
    }

    @Test
    public void whenClientDataNotValidReturnOptionalEmpty(){
        doReturn(false).when(decisionSystem).isLoanGiven(any());
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);
        Assert.assertFalse(optionalProduct.isPresent());
    }

    @Test
    public void whenClientDataValidReturnProduct(){
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
}
