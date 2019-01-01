package com.example.springLoan.DeprecatedRepository;

import com.example.springLoan.model.Product;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

@Ignore
public class ProductRepositoryTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Override
    protected String getMappingString() {
        return RepositoryTestController.PRODUCTS_MAPPING;
    }

    @Test
    public void productTypes() throws Exception {
        //given
        String uri = repositoryTestGetURI();

        //when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

        //then
        Assert.assertEquals(200, response.getStatus());

        Set<Product> products = (Set<Product>) fromJsonToSet(response.getContentAsString(),
                new TypeToken<Set<Product>>(){}.getType());

        for (Product product : products) {
            System.out.println(product);
            System.out.println(product.getCustomer());
            System.out.println(product.getProductType());
        }
    }
}
