package com.example.springLoan.deprecatedRepository;

import com.example.springLoan.model.DataType;
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
public class DataTypeRepositoryTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Override
    protected String getMappingString() {
        return RepositoryTestController.DATA_TYPES_MAPPING;
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

        Set<DataType> dataTypes = (Set<DataType>) fromJsonToSet(response.getContentAsString(),
                new TypeToken<Set<DataType>>(){}.getType());

        Assert.assertEquals(4, dataTypes.size());
    }
}
