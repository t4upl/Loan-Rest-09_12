package com.example.springLoan.service;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.repository.ProductTypeRepository;
import com.example.springLoan.repository.ProductTypeSettingRepository;
import com.example.springLoan.util.constant.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ProductTypeSettingServiceImpl implements ProductTypeSettingService {

    @Autowired
    ProductTypeSettingRepository productTypeSettingRepository;

    @Override
    public Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, String key) {
        return Integer.parseInt(findAndGetAsObject(productTypeSettings, key, EntityUtil.DataType.INTEGER));
    }

    @Override
    public LocalTime findAndGetAsLocalTime(List<ProductTypeSetting> productTypeSettings, String key) {
        return LocalTime.parse(findAndGetAsObject(productTypeSettings, key, EntityUtil.DataType.LOCAL_TIME));
    }

    @Override
    public Double findAndGetAsDouble(List<ProductTypeSetting> productTypeSettings, String key) {
        return Double.parseDouble(findAndGetAsObject(productTypeSettings, key, EntityUtil.DataType.DOUBLE));
    }

    @Override
    public List<ProductTypeSetting> findByProductType_Id(Integer productTypeId) {
        return productTypeSettingRepository.findByProductType_Id(productTypeId);
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
