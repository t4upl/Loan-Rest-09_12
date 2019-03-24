package com.example.springLoan.service;

import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

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
    public Optional<Product> getLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return Optional.of(insertProduct(clientDataWrapper));
        }

        return Optional.empty();
    }

    @Transactional
    private Product insertProduct(ClientDataWrapper clientDataWrapper) {
        Customer customer = customerService.findById(clientDataWrapper.getCustomerId())
                .orElseThrow(() -> new RuntimeException("customerRepository.findById"));

        ProductType productType = productTypeService.findById(clientDataWrapper.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("productTypeRepository.findById"));

        Product product = abstractFactory.getProductFactory()
                .getProduct(-1, customer, productType, null);

        Set<ProductSetting> productSettings = productSettingService.getProductSettings(clientDataWrapper);
        productSettings.forEach(x -> x.setProduct(product));
        return productRepository.save(product);
    }


}
