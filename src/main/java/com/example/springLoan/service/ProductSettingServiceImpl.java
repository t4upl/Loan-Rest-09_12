package com.example.springLoan.service;

import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.Product;
import com.example.springLoan.model.ProductSetting;
import com.example.springLoan.model.ProductTypeSetting;
import com.example.springLoan.model.Setting;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.repository.ProductSettingRepository;
import com.example.springLoan.util.FilterUtil;
import com.example.springLoan.util.constant.EntityUtil;
import lombok.AllArgsConstructor;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductSettingServiceImpl implements ProductSettingService {

    ProductSettingRepository productSettingRepository;
    ProductTypeSettingService productTypeSettingService;
    AbstractFactory abstractFactory;

    @Override
    public List<ProductSetting> saveAll(Iterable<ProductSetting> productSettings) {
        return productSettingRepository.saveAll(productSettings);
    }

    @Override
    public Set<ProductSetting> getProductSettings(ClientDataWrapper clientDataWrapper) {
        List<ProductTypeSetting> productTypeSettings = productTypeSettingService.findByProductType_Id(
                clientDataWrapper.getProductTypeId());

        return productTypeSettings
                .stream()
                .map(pts -> mapProductTypeSettingToProductSetting(pts, clientDataWrapper, productTypeSettings))
                .collect(Collectors.toSet());
    }

    private ProductSetting mapProductTypeSettingToProductSetting(ProductTypeSetting productTypeSetting,
                                                                 ClientDataWrapper clientDataWrapper,
                                                                 List<ProductTypeSetting> productTypeSettings) {
        if (!productTypeSetting.getSetting().getIsRuntimeInput()) {
            return getProductSetting(productTypeSetting, productTypeSetting.getValue());
        }

        return getProductSetting(productTypeSetting,
                getValueForProductSetting(productTypeSetting, clientDataWrapper, productTypeSettings));
    }

    private ProductSetting getProductSetting(ProductTypeSetting productTypeSetting, String value) {
        return abstractFactory.getProductSettingFactory()
                .getProductSetting(-1, value, null, productTypeSetting.getSetting());
    }

    private String getValueForProductSetting(ProductTypeSetting productTypeSetting,
                                             ClientDataWrapper clientDataWrapper,
                                             List<ProductTypeSetting> productTypeSettings) {

        String dataTypeName = productTypeSetting.getSetting().getDataType().getName();
        Optional<Object> javaObjectOpt = Optional.empty();

        switch (productTypeSetting.getSetting().getName()) {
            case (EntityUtil.Setting.APPLICATION_DATE):
                javaObjectOpt = Optional.of(clientDataWrapper.getApplicationDate());
                break;
            case (EntityUtil.Setting.AMOUNT):
                javaObjectOpt = Optional.of(clientDataWrapper.getAmount());
                break;
            case (EntityUtil.Setting.DUE_DATE):
                javaObjectOpt = Optional.of(clientDataWrapper.getApplicationDate().plusDays(
                        clientDataWrapper.getTerm()));
                break;
            case (EntityUtil.Setting.AMOUNT_TO_PAY):
                double rateOfIntrest = productTypeSettingService.findAndGetAsDouble(productTypeSettings,
                        EntityUtil.Setting.RATE_OF_INTEREST);
                javaObjectOpt = Optional.of(Double.valueOf(clientDataWrapper.getAmount())
                        * (1 + rateOfIntrest / 100));
                break;
            case (EntityUtil.Setting.TERM):
                javaObjectOpt = Optional.of(clientDataWrapper.getTerm());
                break;
        }

        if (javaObjectOpt.isPresent()) {
            return FilterUtil.convertJavaToString(javaObjectOpt.get(), dataTypeName);
        }
        throw new RuntimeException(String.format("getValueForProductSetting - case not found for Setting with name %s",
                productTypeSetting.getSetting().getName()));
    }
}
