package com.example.springLoan_18112018.Repository;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.model.DataType;
import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.model.ProductType;
import com.example.springLoan_18112018.repository.CustomerRepository;
import com.example.springLoan_18112018.repository.DataTypeRepository;
import com.example.springLoan_18112018.repository.ProductRepository;
import com.example.springLoan_18112018.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.springLoan_18112018.Repository.RepositoryController.CLASS_MAPPING;

/***
 * This class used to test repository classes.
 *
 */

@RestController
@RequestMapping(CLASS_MAPPING)
public class RepositoryController {

    public static final String CLASS_MAPPING = "/repository";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }

    @RequestMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(value = "/product-types")
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    @RequestMapping(value = "/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/data-types")
    public List<DataType> getDataTypes() {
        return dataTypeRepository.findAll();
    }


}
