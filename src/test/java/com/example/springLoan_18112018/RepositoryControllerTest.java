package com.example.springLoan_18112018;

import com.example.springLoan_18112018.model.Customer;
import com.example.springLoan_18112018.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RepositoryControllerTest extends AbstractControllerTest {

    @Autowired
    private RepositoryController repositoryController;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void sanityTest() {
        Assert.assertNotNull(repositoryController);
        System.out.println("sanity Test");
    }

    @Test
    public void customers() throws Exception {
        String uri = "/repository/customers";
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

        Assert.assertEquals(200, response.getStatus());
        String content  = response.getContentAsString();

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Johny");

        Assert.assertTrue(((Set<Customer>) fromJsonToSet(content,
                new TypeToken<Set<Customer>>(){}.getType())).contains(customer));
    }
}
