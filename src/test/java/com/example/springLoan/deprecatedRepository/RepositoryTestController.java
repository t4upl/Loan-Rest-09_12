package com.example.springLoan.deprecatedRepository;

import com.example.springLoan.model.*;
import com.example.springLoan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.springLoan.deprecatedRepository.RepositoryTestController.CLASS_MAPPING;

/***
 * This class used to test repository classes.
 *
 */

@RestController
@RequestMapping(CLASS_MAPPING)
public class RepositoryTestController {

    public static final String CLASS_MAPPING = "/repository";
    public static final String CUSTOMERS_MAPPING = "/customers";
    public static final String PRODUCT_TYPES_MAPPING = "/product-types";
    public static final String PRODUCTS_MAPPING = "/products";
    public static final String DATA_TYPES_MAPPING = "/data-types";
    public static final String SETTINGS_MAPPING = "/settings";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SettingRepository settingRepository;

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }

    @RequestMapping(value = CUSTOMERS_MAPPING)
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(value = PRODUCT_TYPES_MAPPING)
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    @RequestMapping(value = PRODUCTS_MAPPING)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(value = DATA_TYPES_MAPPING)
    public List<DataType> getDataTypes() {
        return dataTypeRepository.findAll();
    }

    @RequestMapping(value = SETTINGS_MAPPING)
    public List<Setting> getSettings() {
        return settingRepository.findAll();
    }


}
