package com.example.springLoan_18112018.Repository;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.model.ProductType;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

public class ProductTypeRepositoryTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void productTypes() throws Exception {
        //given
        String uri = "/repository/product-types";

        //when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

        //then
        Assert.assertEquals(200, response.getStatus());

        Set<ProductType> productTypes = (Set<ProductType>) fromJsonToSet(response.getContentAsString(),
                new TypeToken<Set<ProductType>>(){}.getType());

        for (ProductType productType : productTypes) {
            System.out.println(productType);
            Set<Product> products = productType.getProducts();
            for (Product product : products) {
                System.out.println(product);
                System.out.println(product.getProductType());
                System.out.println(product.getCustomer());
            }
        }

        System.out.println(productTypes.size());
    }
}
