package com.example.springLoan.deprecatedRepository;

import com.example.springLoan.model.Customer;
import com.example.springLoan.model.Setting;
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
public class SettingRepositoryTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Override
    protected String getMappingString() {
        return RepositoryTestController.SETTINGS_MAPPING;
    }

    @Test
    public void sanityTest() {
        System.out.println("sanity Test");
    }

    @Test
    public void settings() throws Exception {
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

        Set<Setting> settings = (Set<Setting>) fromJsonToSet(response.getContentAsString(),
                new TypeToken<Set<Setting>>(){}.getType());

        settings.stream().forEach(x -> System.out.println(x));

        final String minAmountNameSetting = "min amount";
        Setting minAmountSetting =  settings
                .stream()
                .filter(x -> x.getName().equals(minAmountNameSetting))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No proepr"));

        System.out.println(minAmountSetting);
        System.out.println(minAmountSetting.getDataType());
    }
}
