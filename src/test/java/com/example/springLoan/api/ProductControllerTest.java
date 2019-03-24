package com.example.springLoan.api;

import com.example.springLoan.model.Product;
import com.example.springLoan.service.ProductService;
import com.example.springLoan.util.TestingUtil;
import com.example.springLoan.util.constant.ProductControllerConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void whenSanityTestReturnOk() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get(ProductControllerConstant.SANITY_TEST_PATH)
                .accept(MediaType.TEXT_PLAIN)).andReturn();

        //then
        Assert.assertEquals(HttpStatus.OK.value(),  mvcResult.getResponse().getStatus());
        Assert.assertEquals(ProductControllerConstant.SANITY_TEST_RESPONSE,
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void whenApplyForLoanSuccessReturnOk() throws Exception {
        //given
        Integer productId = -1;
        Optional<Product> optionalProduct = Optional.of(new Product(-1, null, null, null));
        when(productService.getLoan(any())).thenReturn(optionalProduct);


        String productRequest = TestingUtil.readFileAsString(
                "test/json/product_request_dto.json");

        //when
        MvcResult mvcResult = mockMvc.perform(post(ProductControllerConstant.APPLY_FOR_LOAN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequest)
                .accept(MediaType.TEXT_PLAIN)).andReturn();

        //then
        Assert.assertEquals(HttpStatus.CREATED .value(),  mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.contains(ProductControllerConstant.APPLY_FOR_LOAN_SUCCESS_RESPONSE));
        Assert.assertTrue(content.contains(ProductControllerConstant.TIME_METADATA));
        Assert.assertTrue(content.contains(ProductControllerConstant.LOAN_ID_METADATA));
    }

    @Test
    public void whenApplyForLoanFailReturnBadRequest() throws Exception {
        //given
        when(productService.getLoan(any())).thenReturn(Optional.empty());
        String productRequestDTO = TestingUtil.readFileAsString(
                "test/json/product_request_dto.json");

        //when
        MvcResult mvcResult = mockMvc.perform(post(ProductControllerConstant.APPLY_FOR_LOAN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestDTO)
                .accept(MediaType.TEXT_PLAIN)).andReturn();

        //then
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),  mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.contains(ProductControllerConstant.APPLY_FOR_LOAN_FAIL_RESPONSE));
        Assert.assertTrue(content.contains(ProductControllerConstant.TIME_METADATA));
        Assert.assertTrue(!content.contains(ProductControllerConstant.LOAN_ID_METADATA));
    }


}
