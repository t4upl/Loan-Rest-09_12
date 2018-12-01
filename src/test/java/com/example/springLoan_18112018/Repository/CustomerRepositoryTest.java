package com.example.springLoan_18112018.Repository;

import com.example.springLoan_18112018.model.Customer;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;


public class CustomerRepositoryTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Override
    protected String getMappingString() {
        return "/customers";
    }

    @Test
    public void sanityTest() {
        System.out.println("sanity Test");
    }

    @Test
    public void customers() throws Exception {
        Customer customerVerify = new Customer();
        customerVerify.setId(1);
        customerVerify.setName("Johny");

        //given
        String uri = repositoryTestGetURI();

        //when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

        //then
        Assert.assertEquals(200, response.getStatus());

        Set<Customer> customers = (Set<Customer>) fromJsonToSet(response.getContentAsString(),
                new TypeToken<Set<Customer>>(){}.getType());
        Object[] objects = customers.toArray();
        Assert.assertEquals(1, objects.length);
        Customer customerRepo = (Customer) objects[0];
        Assert.assertTrue( customerRepo.getId().equals(customerVerify.getId())
                && customerRepo.getName().equals(customerVerify.getName()));
        Assert.assertEquals(1, customerRepo.getProducts().size());
    }
}
