package com.example.springLoan.api;

import com.example.springLoan.model.Product;
import com.example.springLoan.other.ClientDataWrapper;
import com.example.springLoan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.springLoan.util.constant.ProductControllerConstant.*;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * This endpoint is for sanity testing / health check
     */
    @RequestMapping(SANITY_TEST_PATH)
    public ResponseEntity<String> getProduct() {
        return new ResponseEntity<>(SANITY_TEST_RESPONSE, HttpStatus.OK);
    }

    @RequestMapping(value = APPLY_FOR_LOAN_PATH, method = RequestMethod.POST)
    public ResponseEntity<String> applyForLoan(@RequestBody ClientDataWrapper clientDataWrapper){
        Optional<Product> optionalProduct = productService.getLoan(clientDataWrapper);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>(addMetaData(APPLY_FOR_LOAN_SUCCESS_RESPONSE,
                    optionalProduct.get().getId()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(addMetaData(APPLY_FOR_LOAN_FAIL_RESPONSE), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = EXTEND_LOAN_PATH, method = RequestMethod.PATCH)
    public ResponseEntity<String> extendLoan(){
        return null;

    }

    private String addMetaData(String inputMsg){
        return inputMsg + "\n\n" + addRequestTime();
    }

    private String addMetaData(String inputMsg, Integer loanId){
        return addMetaData(inputMsg) + "\t" + LOAN_ID_METADATA + " " + loanId;
    }

    private String addRequestTime(){
        LocalDateTime applicationTime = LocalDateTime.now();
        return TIME_METADATA + " " + applicationTime.toString();
    }
}
