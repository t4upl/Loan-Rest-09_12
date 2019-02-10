package com.example.springLoan.service;

import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.Setting;
import com.example.springLoan.repository.ProductSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductSettingServiceImpl implements ProductSettingService {

    @Autowired
    ProductSettingRepository productSettingRepository;

    @Override
    public ProductSetting getProductSetting(Integer id, String value, Product product, Setting setting) {
        return new ProductSetting(id, value, product, setting);
    }

    @Override
    public List<ProductSetting> saveAll(Iterable<ProductSetting> productSettings) {
        return productSettingRepository.saveAll(productSettings);

//        return productSettingRepository.saveAll(productSettings);
    }
}
