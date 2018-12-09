package com.example.springLoan_18112018.service;

import com.example.springLoan_18112018.model.ProductTypeSetting;
import com.example.springLoan_18112018.util.constant.EntityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeSettingServiceImpl implements ProductTypeSettingService {

    @Override
    public Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, String key) {
        String string = findAndGetAsObject(productTypeSettings, key, EntityUtil.INTEGER);
        return Integer.parseInt(string);
    }

    private String findAndGetAsObject (List<ProductTypeSetting> productTypeSettings, String propertyKey,
                                       String dataTypeKey) {
        ProductTypeSetting productTypeSetting = findProductTypeSettingByValue(productTypeSettings, propertyKey);

        String ptsDataType = productTypeSetting.getSetting().getDataType().getName();
        if (!ptsDataType.equals(dataTypeKey)) {
            throw new RuntimeException(String.format("Error casting productTypeSetting with key: %s of type: %s " +
                    "to type: %s.", propertyKey, ptsDataType, dataTypeKey));
        }

        return productTypeSetting.getValue();
    }

    private ProductTypeSetting findProductTypeSettingByValue(List<ProductTypeSetting> productSettings,
                                                             String key) {
        return productSettings
                .stream()
                .filter(productTypeSetting ->
                        productTypeSetting.getSetting().getName().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("findProductTypeSettingByValue: " + key));
    }
}
