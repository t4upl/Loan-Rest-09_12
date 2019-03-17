package com.example.springLoan.service;

import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.util.FilterUtil;
import com.example.springLoan.util.constant.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    CustomerService customerService;

    ProductTypeService productTypeService;

    ProductSettingService productSettingService;

    ProductTypeSettingService productTypeSettingService;

    DecisionSystem decisionSystem;

    AbstractFactory abstractFactory;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CustomerService customerService,
                              ProductTypeService productTypeService,
                              ProductSettingService productSettingService,
                              ProductTypeSettingService productTypeSettingService,
                              DecisionSystem decisionSystem,
                              AbstractFactory abstractFactory) {
        this.productRepository = productRepository;
        this.customerService = customerService;
        this.productTypeService = productTypeService;
        this.productSettingService = productSettingService;
        this.productTypeSettingService = productTypeSettingService;
        this.decisionSystem = decisionSystem;
        this.abstractFactory = abstractFactory;
    }

    @Override
    @Transactional
    public Optional<Product> getLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return Optional.of(insertProduct(clientDataWrapper));
        }

        return Optional.empty();
    }

    private Product insertProduct(ClientDataWrapper clientDataWrapper) {
        Customer customer = customerService.findById(clientDataWrapper.getCustomerId())
                .orElseThrow(() -> new RuntimeException("customerRepository.findById"));

        ProductType productType = productTypeService.findById(clientDataWrapper.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("productTypeRepository.findById"));

        return productRepository.save(abstractFactory.getProductFactory()
                .getProduct(-1, customer, productType, getProductSettingsInProduct(clientDataWrapper)));
    }

    private Set<ProductSetting> getProductSettingsInProduct(ClientDataWrapper clientDataWrapper){
        Set<ProductSetting> productSettings = getProductSettings(clientDataWrapper);
        return new HashSet<>(productSettingService.saveAll(productSettings));
    }

    private Set<ProductSetting> getProductSettings(ClientDataWrapper clientDataWrapper) {
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
        switch (productTypeSetting.getSetting().getName()) {
            case (EntityUtil.Setting.APPLICATION_DATE):
                return FilterUtil.localDateTimeToString(clientDataWrapper.getApplicationDate());
            case (EntityUtil.Setting.AMOUNT):
                return FilterUtil.integerToString(clientDataWrapper.getAmount());
            case (EntityUtil.Setting.DUE_DATE):
                return FilterUtil.localDateTimeToString(clientDataWrapper.getApplicationDate().plusDays(
                        clientDataWrapper.getTerm()));
            case (EntityUtil.Setting.AMOUNT_TO_PAY):
                double rateOfIntrest = productTypeSettingService.findAndGetAsDouble(productTypeSettings,
                        EntityUtil.Setting.RATE_OF_INTEREST);
                return FilterUtil.doubleToString(Double.valueOf(clientDataWrapper.getAmount())
                        * (1 + rateOfIntrest / 100));
            case (EntityUtil.Setting.TERM):
                return FilterUtil.integerToString(clientDataWrapper.getTerm());
        }

        throw new RuntimeException(String.format("getValueForProductSetting - case not found for Setting with name %s",
                productTypeSetting.getSetting().getName()));
    }


}
