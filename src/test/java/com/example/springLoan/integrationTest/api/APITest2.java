package com.example.springLoan.integrationTest.api;

import com.example.springLoan.api.API;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = API.class, secure = false)
public class APIITest2 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sanityEndpointTest(){

    }

}
