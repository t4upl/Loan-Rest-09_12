package com.example.springLoan.service;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.util.constant.EntityUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTypeSettingServiceTest {

    @Autowired
    ProductTypeSettingService productTypeSettingService;

    List<ProductTypeSetting> productTypeSettings;

    @Before
    public void setUp() {
        productTypeSettings = new ArrayList<>();
        ProductTypeSetting productTypeSetting = mock(ProductTypeSetting.class, Mockito.RETURNS_DEEP_STUBS);
        when(productTypeSetting.getSetting().getName()).thenReturn("min amount");
        when(productTypeSetting.getSetting().getDataType().getName()).thenReturn(EntityUtil.DataType.INTEGER);
        when(productTypeSetting.getValue()).thenReturn("-1");
        productTypeSettings.add(productTypeSetting);
    }

    @Test
    public void returnProductTypeSettingOnfindAndGetAsInteger(){
        String name = "name";
        ProductTypeSetting productTypeSetting = mock(ProductTypeSetting.class, Mockito.RETURNS_DEEP_STUBS);

        //given
        when(productTypeSetting.getSetting().getName()).thenReturn(name);
        when(productTypeSetting.getSetting().getDataType().getName()).thenReturn(EntityUtil.DataType.INTEGER);
        when(productTypeSetting.getValue()).thenReturn("999");
        productTypeSettings.add(productTypeSetting);

        //when
        Integer integer = productTypeSettingService.findAndGetAsInteger(productTypeSettings, name);

        //then
        Assert.assertEquals(999, integer.longValue());
    }

    @Test (expected = RuntimeException.class)
    public void throwExceptionWhenBadDataTypeName(){
        String name = "name";
        ProductTypeSetting productTypeSetting = mock(ProductTypeSetting.class, Mockito.RETURNS_DEEP_STUBS);

        //given
        when(productTypeSetting.getSetting().getName()).thenReturn(name);
        when(productTypeSetting.getSetting().getDataType().getName()).thenReturn(EntityUtil.DataType.DOUBLE);
        when(productTypeSetting.getValue()).thenReturn("999");
        productTypeSettings.add(productTypeSetting);

        //when
        productTypeSettingService.findAndGetAsInteger(productTypeSettings, name);
    }

    @Test (expected = RuntimeException.class)
    public void throwExceptionWhenNoRroductTypeSettingWithName(){
        productTypeSettingService.findAndGetAsInteger(productTypeSettings, "no such name in list");
    }
}
