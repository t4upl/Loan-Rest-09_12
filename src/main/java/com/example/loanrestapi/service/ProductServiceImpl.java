package com.example.loanrestapi.service;

import com.example.loanrestapi.decisionsystem.DecisionSystem;
import com.example.loanrestapi.dto.ProductRequestDto;
import com.example.loanrestapi.factory.AbstractFactory;
import com.example.loanrestapi.model.Customer;
import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.model.ProductSetting;
import com.example.loanrestapi.model.ProductType;
import com.example.loanrestapi.repository.ProductRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CustomerService customerService;
  private final ProductTypeService productTypeService;
  private final ProductSettingService productSettingService;
  private final DecisionSystem decisionSystem;
  private final AbstractFactory abstractFactory;

  @Override
  public Optional<Product> getLoan(ProductRequestDto productRequestDto) {
    if (decisionSystem.isLoanGiven(productRequestDto)) {
      return Optional.of(insertProduct(productRequestDto));
    }

    return Optional.empty();
  }

  @Override
  public Optional<Product> extendLoan(Long productId) {
    Optional<Product> productOptional = productRepository.findById(productId);
    if (!productOptional.isPresent()) {
      return productOptional;
    }
    Product product = productOptional.get();
    product.setProductSettings(
        productSettingService.addExtensionTermToDueDate(product.getProductSettings()));
    return Optional.of(productRepository.save(product));
  }

  @Transactional
  private Product insertProduct(ProductRequestDto productRequestDto) {
    Customer customer = customerService.findById(productRequestDto.getCustomerId())
        .orElseThrow(() -> new RuntimeException("customerRepository.findById"));

    ProductType productType = productTypeService.findById(productRequestDto.getProductTypeId())
        .orElseThrow(() -> new RuntimeException("productTypeRepository.findById"));

    Product product = abstractFactory.getProductFactory()
        .getProduct(null, customer, productType, new HashSet<>());

    Set<ProductSetting> productSettings = productSettingService
        .getProductSettings(productRequestDto);
    productSettings.forEach(product::addProductSetting);
    return productRepository.save(product);
  }
}
