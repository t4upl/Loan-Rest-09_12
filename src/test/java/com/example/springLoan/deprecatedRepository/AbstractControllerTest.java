package com.example.springLoan.deprecatedRepository;

import com.example.springLoan.SpringLoanRestAPI;
import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringLoanRestAPI.class)
@WebAppConfiguration
public abstract class AbstractControllerTest {

    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected Object fromJsonToSet(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    /***
     * Note: While overriding add "/" in front
     */
    protected abstract String getMappingString();

    protected String repositoryTestGetURI(){
        return RepositoryTestController.CLASS_MAPPING + getMappingString();
    }

}
