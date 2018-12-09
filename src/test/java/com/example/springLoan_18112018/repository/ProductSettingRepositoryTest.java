package com.example.springLoan_18112018.repository;

import com.example.springLoan_18112018.model.ProductSetting;
import com.example.springLoan_18112018.model.ProductTypeSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ProductSettingRepositoryTest {

    @Autowired
    ProductSettingRepository productSettingRepository;

    @Test
    public void findByIdTest(){
        Long id = 1L;
        ProductSetting productSetting  = productSettingRepository.findById(1)
                .orElseThrow(() -> new RuntimeException(String.format("No entry with id %d", id)));

        Assert.assertEquals("1000", productSetting.getValue());
        Assert.assertEquals(1, productSetting.getProduct().getId().longValue());
        Assert.assertEquals(1, productSetting.getSetting().getId().longValue());
    }
}
