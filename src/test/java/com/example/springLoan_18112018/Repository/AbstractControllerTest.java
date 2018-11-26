package com.example.springLoan_18112018.Repository;

import com.example.springLoan_18112018.SpringLoan18112018Application;
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
@SpringBootTest(classes = SpringLoan18112018Application.class)
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
}
