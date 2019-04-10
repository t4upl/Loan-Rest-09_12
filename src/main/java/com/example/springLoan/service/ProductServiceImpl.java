package com.example.springLoan.service;

import com.example.springLoan.dto.ProductRequestDTO;
import com.example.springLoan.factory.AbstractFactory;
import com.example.springLoan.model.*;
import com.example.springLoan.decision_system.DecisionSystem;
import com.example.springLoan.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public Optional<Product> getLoan(ProductRequestDTO productRequestDTO) {
        if (decisionSystem.isLoanGiven(productRequestDTO)) {
            return Optional.of(insertProduct(productRequestDTO));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Product> extendLoan(Long productId){
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return productOptional;
        }
        Product product = productOptional.get();
        product.setProductSettings(productSettingService.addExtensionTermToDueDate(product.getProductSettings()));
        return Optional.of(productRepository.save(product));
    }

    @Transactional
    private Product insertProduct(ProductRequestDTO productRequestDTO) {
        Customer customer = customerService.findById(productRequestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("customerRepository.findById"));

        ProductType productType = productTypeService.findById(productRequestDTO.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("productTypeRepository.findById"));

        Product product = abstractFactory.getProductFactory()
                .getProduct(null, customer, productType, new HashSet<>());

        Set<ProductSetting> productSettings = productSettingService.getProductSettings(productRequestDTO);
        productSettings.forEach(product::addProductSetting);
        return productRepository.save(product);
    }
}
