package com.example.springLoan_18112018.repository;

import com.example.springLoan_18112018.model.ProductTypeSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ProductTypeSettingRepositoryTest {

    @Autowired
    ProductTypeSettingRepository productTypeSettingRepository;

    @Test
    public void findByIdTest(){
        Long id = 1L;
        ProductTypeSetting productTypeSetting  = productTypeSettingRepository.findById(1)
                .orElseThrow(() -> new RuntimeException(String.format("No entry with id %d", id)));

        Assert.assertEquals("1000", productTypeSetting.getValue());
        Assert.assertEquals("test product type", productTypeSetting.getProductType().getName());
        Assert.assertEquals("min amount", productTypeSetting.getSetting().getName());
    }

    @Test
    public void shouldReturnAllProductTypeSettingsWithProductTypeId(){
        List<ProductTypeSetting> productTypeSettings = productTypeSettingRepository
                .findByProductType_Id(1);

        Assert.assertEquals(14, productTypeSettings.size());
        Assert.assertTrue(productTypeSettings
                .stream()
                .allMatch(x -> x.getProductType().getName().equals("test product type")));

    }
}
