package com.example.springLoan.service;

import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.repository.ProductTypeSettingRepository;
import com.example.springLoan.util.constant.DataTypeConstant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductTypeSettingServiceImpl implements ProductTypeSettingService {

    ProductTypeSettingRepository productTypeSettingRepository;

    @Override
    public Integer findAndGetAsInteger(List<ProductTypeSetting> productTypeSettings, String key) {
        return Integer.parseInt(findAndGetAsObject(productTypeSettings, key, DataTypeConstant.INTEGER));
    }

    @Override
    public LocalTime findAndGetAsLocalTime(List<ProductTypeSetting> productTypeSettings, String key) {
        return LocalTime.parse(findAndGetAsObject(productTypeSettings, key, DataTypeConstant.LOCAL_TIME));
    }

    @Override
    public Double findAndGetAsDouble(List<ProductTypeSetting> productTypeSettings, String key) {
        return Double.parseDouble(findAndGetAsObject(productTypeSettings, key, DataTypeConstant.DOUBLE));
    }

    @Override
    public List<ProductTypeSetting> findByProductType_Id(Long productTypeId) {
        return productTypeSettingRepository.findByProductType_Id(productTypeId);
    }

    private String findAndGetAsObject (List<ProductTypeSetting> productTypeSettings, String propertyKey,
                                       String dataTypeKey) {
        ProductTypeSetting productTypeSetting = findProductTypeSettingByName(productTypeSettings, propertyKey);

        String ptsDataType = productTypeSetting.getSetting().getDataType().getName().toString();
        if (!ptsDataType.equals(dataTypeKey)) {
            throw new RuntimeException(String.format("Error casting productTypeSetting with key: %s of type: %s " +
                    "to type: %s.", propertyKey, ptsDataType, dataTypeKey));
        }

        return productTypeSetting.getValue();
    }

    private ProductTypeSetting findProductTypeSettingByName(List<ProductTypeSetting> productSettings,
                                                            String key) {
        return productSettings
                .stream()
                .filter(productTypeSetting ->
                        productTypeSetting.getSetting().getName().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("findProductTypeSettingByName: " + key));
    }
}
