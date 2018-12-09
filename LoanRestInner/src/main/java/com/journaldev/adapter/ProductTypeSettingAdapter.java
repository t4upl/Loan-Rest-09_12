package com.journaldev.adapter;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;

public interface ProductTypeSettingAdapter {
    ProductSetting getProductSetting(ProductTypeSetting productTypeSetting, int productId);
}
