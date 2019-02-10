package com.example.springLoan.service;

import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.other.decision_system.DecisionSystem;
import com.example.springLoan.repository.CustomerRepository;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.repository.ProductTypeRepository;
import com.example.springLoan.repository.ProductTypeSettingRepository;
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

//    @Autowired
    ProductRepository productRepository;

//    @Autowired
    CustomerRepository customerRepository;

//    @Autowired
    ProductTypeRepository productTypeRepository;

//    @Autowired
    ProductTypeSettingRepository productTypeSettingRepository;

//    @Autowired
    ProductSettingService productSettingService;

//    @Autowired
    ProductTypeSettingService productTypeSettingService;

//    @Autowired
    DecisionSystem decisionSystem;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CustomerRepository customerRepository,
                              ProductTypeRepository productTypeRepository,
                              ProductTypeSettingRepository productTypeSettingRepository,
                              ProductSettingService productSettingService,
                              ProductTypeSettingService productTypeSettingService,
                              DecisionSystem decisionSystem) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.productTypeRepository = productTypeRepository;
        this.productTypeSettingRepository = productTypeSettingRepository;
        this.productSettingService = productSettingService;
        this.productTypeSettingService = productTypeSettingService;
        this.decisionSystem = decisionSystem;
    }

    @Override
    @Transactional
    public Optional<Product> getLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return Optional.of(insertProduct(clientDataWrapper));
        }

        return Optional.empty();
    }

    private ProductSetting mapProductTypeSettingToProductSetting(ProductTypeSetting productTypeSetting,
                                                                 Product product, ClientDataWrapper clientDataWrapper,
                                                                 List<ProductTypeSetting> productTypeSettings) {

        if (!productTypeSetting.getSetting().getIsRuntimeInput()) {
            return getProductSetting(productTypeSetting, product, productTypeSetting.getValue());
        }

        return getProductSetting(productTypeSetting, product,
                getValueForProductSetting(productTypeSetting, clientDataWrapper, productTypeSettings));
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


    private ProductSetting getProductSetting(ProductTypeSetting productTypeSetting, Product product, String value) {
        return productSettingService.getProductSetting(-1, value, product, productTypeSetting.getSetting());
    }


    private Set<ProductSetting> getProductSettings(Product product, ClientDataWrapper clientDataWrapper) {
        List<ProductTypeSetting> productTypeSettings = productTypeSettingRepository.findByProductType_Id(
                clientDataWrapper.getProductTypeId());

        return productTypeSettings
                .stream()
                .map(pts -> mapProductTypeSettingToProductSetting(pts, product, clientDataWrapper, productTypeSettings))
                .collect(Collectors.toSet());
    }

    private Product insertProduct(ClientDataWrapper clientDataWrapper) {
        Customer customer = customerRepository.findById(clientDataWrapper.getCustomerId())
                .orElseThrow(() -> new RuntimeException("customerRepository.findById"));

        ProductType productType = productTypeRepository.findById(clientDataWrapper.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("productTypeRepository.findById"));

        Product product = new Product(-1, customer, productType, new HashSet<>());
        product = productRepository.save(product);
        setProductSettingsInProduct(product, clientDataWrapper);

        return product;
    }

    private void setProductSettingsInProduct(Product product, ClientDataWrapper clientDataWrapper){
        Set<ProductSetting> productSettings = getProductSettings(product, clientDataWrapper);
        List<ProductSetting> productSettings1 = productSettingService.saveAll(productSettings);
        product.setProductSettings(new HashSet<ProductSetting>(productSettings1));
    }
}
