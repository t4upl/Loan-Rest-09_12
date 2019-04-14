package com.example.loanrestapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.example.loanrestapi.AbstractTest;
import com.example.loanrestapi.decisionsystem.DecisionSystem;
import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.factory.AbstractFactory;
import com.example.loanrestapi.model.Customer;
import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductType;
import com.example.loanrestapi.repository.ProductRepository;
import com.example.loanrestapi.util.TestingUtil;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void setUp(){
        this.productRepository = Mockito.mock(ProductRepository.class,
                new NullPointerExceptionAnswer("productRepository"));
        this.customerService = Mockito.mock(CustomerService.class, new NullPointerExceptionAnswer("customerService"));
        this.productTypeService = Mockito.mock(ProductTypeService.class,
                new NullPointerExceptionAnswer("productTypeService"));
        this.productSettingService = Mockito.mock(ProductSettingService.class,
                new NullPointerExceptionAnswer("productSettingService"));
        this.productTypeSettingService = Mockito.mock(ProductTypeSettingService.class,
                new NullPointerExceptionAnswer("productTypeSettingService "));
        this.decisionSystem = Mockito.mock(DecisionSystem.class, new NullPointerExceptionAnswer("decisionSystem"));
        this.abstractFactory = Mockito.mock(AbstractFactory.class, Mockito.RETURNS_DEEP_STUBS);


        this.productService = new ProductServiceImpl(productRepository, customerService,
            productTypeService, productSettingService, decisionSystem, abstractFactory);

    }

    @Test
    public void whenClientDataNotValidReturnOptionalEmpty(){
        doReturn(false).when(decisionSystem).isLoanGiven(any());
        Optional<Product> optionalProduct = productService.getLoan(null);
        Assert.assertFalse(optionalProduct.isPresent());
    }

    @Test
    public void whenClientDataValidReturnProduct(){
        //given
        ProductRequestDto productRequestDto = TestingUtil.getProductRequestDTO(null,
                "1986-04-08 12:30:00", 15);

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

        //when
        Optional<Product> optionalProduct = productService.getLoan(productRequestDto);

        //then
        Assert.assertTrue(optionalProduct.isPresent());

        Product product = optionalProduct.get();
        Set<ProductSetting> productSettings = product.getProductSettings();
        Assert.assertEquals("There should be 1 element in productSettings list",1,
                productSettings.size());
    }

    @Test
    public void whenExtendLoanAndLoanNotInDBReturnOptionalEmpty(){
        //given
        doReturn(Optional.empty()).when(productRepository).findById(any());

        //when
        Optional<Product> optionalProduct = productService.extendLoan(1L);

        //then
        Assert.assertEquals("If Product with Id not found productService should return optionalEmpty",
                Optional.empty(), optionalProduct);
    }

    @Test
    public void whenExtendLoanAndLoanInDBReturnProduct(){
        //given
        doReturn(Optional.of(new Product(null, null, null, new HashSet<>())))
                .when(productRepository).findById(any());

        HashSet<ProductSetting> productSettings = new HashSet<>();
        productSettings.add(new ProductSetting());
        doReturn(productSettings).when(productSettingService).addExtensionTermToDueDate(any());
        doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(productRepository).save(any());

        //when
        Optional<Product> optionalProduct = productService.extendLoan(null);

        //then
        Assert.assertTrue("optionalProduct should not be empty", optionalProduct.isPresent());
        Assert.assertEquals("Product should have productSettings set by external srvice: " +
                "'addExtensionTermToDueDate' method", productSettings.size(), optionalProduct.get().getProductSettings().size());
    }


}
