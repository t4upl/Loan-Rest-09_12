package com.journaldev.adapter;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.factory.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductTypeSettingAdapterImpl implements ProductTypeSettingAdapter {

    @Autowired
    EntityFactory entityFactory;

    @Override
    public ProductSetting getProductSetting(ProductTypeSetting productTypeSetting, int productId) {
        return entityFactory.getProductSetting(-1, productId, productTypeSetting.getSettingTypeId(),
                                                                                    productTypeSetting.getValue());
    }
}
