package com.example.springLoan.integrationTest.api;

import com.example.springLoan.api.API;
import com.example.springLoan.repository.CustomerRepository;
import com.example.springLoan.repository.ProductRepository;
import com.example.springLoan.service.ProductService;
import com.example.springLoan.util.constant.ApplicationConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sanityEndpointTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sanity-test").accept(MediaType.TEXT_PLAIN);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(ApplicationConstant.SANITY_TEST_ENDPOINT_RESPONSE,
                result.getResponse().getContentAsString());
    }

}
