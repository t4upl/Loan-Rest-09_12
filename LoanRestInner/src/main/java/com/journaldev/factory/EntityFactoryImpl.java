package com.journaldev.factory;

import com.journaldev.entity.*;
import lombok.NonNull;

public class EntityFactoryImpl implements EntityFactory {

    @Override
    public Customer getCustomer (int id, String name) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        return customer;
    }

    @Override
    public Product getProduct(int id, int productTypeId, int customerId) {
        Product product = new Product();
        product.setId(id);
        product.setProductTypeId(productTypeId);
        product.setCustomerId(customerId);
        return product;
    }

    @Override
    public ProductTypeSetting getProductTypeSetting(Integer id, Integer productTypeId, Integer settingTypeId,
                                                    String value) {
        ProductTypeSetting productTypeSetting = new ProductTypeSetting();
        productTypeSetting.setId(id);
        productTypeSetting.setProductTypeId(productTypeId);
        productTypeSetting.setSettingTypeId(settingTypeId);
        productTypeSetting.setValue(value);
        return productTypeSetting;
    }

    @Override
    public ProductSetting getProductSetting(Integer id, int productId, int setttingTypeId, String value) {
        ProductSetting productSetting = new ProductSetting();
        productSetting.setId(id);
        productSetting.setProductId(productId);
        productSetting.setSettingTypeId(setttingTypeId);
        productSetting.setValue(value);
        return productSetting;
    }

    @Override
    public SettingType getSettingType(int id, String name) {
        SettingType settingType = new SettingType();
        settingType.setId(id);
        settingType.setName(name);
        return settingType;
    }

}
