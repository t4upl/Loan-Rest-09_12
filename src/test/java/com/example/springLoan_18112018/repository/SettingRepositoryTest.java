package com.example.springLoan_18112018.repository;

import com.example.springLoan_18112018.model.DataType;
import com.example.springLoan_18112018.model.Setting;
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
public class SettingRepositoryTest {

    @Autowired
    SettingRepository settingRepository;

    @Test
    public void findByIdTest(){
        Long id = 1L;
        Setting setting = settingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("No setting with id %d", id)));

        Assert.assertEquals("min amount", setting.getName());
        Assert.assertFalse(setting.getIsRuntimeInput());
        DataType dataType = setting.getDataType();
        Assert.assertEquals(1, (long) dataType.getId());
        Assert.assertEquals("Integer", dataType.getName());
    }

    @Test
    public void findAllTest(){
        List<Setting> settings = settingRepository.findAll();
        Assert.assertEquals(14, settings.size());
    }
}
