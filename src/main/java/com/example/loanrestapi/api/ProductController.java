package com.example.loanrestapi.api;

import static com.example.loanrestapi.util.constant.ProductControllerConstant.ACTION;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.APPLY_FOR_LOAN_FAIL_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.APPLY_FOR_LOAN_SUCCESS_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.EXTEND_LOAN_ACTION;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.EXTEND_LOAN_FAIL_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.EXTEND_LOAN_SUCCESS_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.GET_PRODUCT_PATH;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.LOAN_ID_METADATA;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.PATCH_PRODUCT_ACTION_UNKNOWN_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.PATCH_PRODUCT_PATH;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.SANITY_TEST_PATH;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.SANITY_TEST_RESPONSE;
import static com.example.loanrestapi.util.constant.ProductControllerConstant.TIME_METADATA;

import com.example.loanrestapi.dto.ProductRequestDTO;
import com.example.loanrestapi.model.Product;
import com.example.loanrestapi.service.ProductService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("CheckStyle")
@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  /** This endpoint is for sanity testing / health check.*/
  @RequestMapping(SANITY_TEST_PATH)
  public ResponseEntity<String> getSanityTest() {
    return new ResponseEntity<>(SANITY_TEST_RESPONSE, HttpStatus.OK);
  }

  /** Endpoint for posting new products (loans). */
  @RequestMapping(value = GET_PRODUCT_PATH, method = RequestMethod.POST)
  public ResponseEntity<String> postProduct(@RequestBody ProductRequestDTO productRequestDTO) {
    Optional<Product> optionalProduct = productService.getLoan(productRequestDTO);
    if (optionalProduct.isPresent()) {
      return new ResponseEntity<>(addMetaData(APPLY_FOR_LOAN_SUCCESS_RESPONSE,
                   optionalProduct.get().getId()), HttpStatus.CREATED);
    }
    return new ResponseEntity<>(addMetaData(APPLY_FOR_LOAN_FAIL_RESPONSE), HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = PATCH_PRODUCT_PATH, method = RequestMethod.PATCH)
  public ResponseEntity<String> patchProduct(@PathVariable("id") Long productId,
                                               @RequestParam(ACTION) String action) {
    if (EXTEND_LOAN_ACTION.equals(action)) {
      return extendLoan(productId);
    }
    return new ResponseEntity<>(addMetaData(PATCH_PRODUCT_ACTION_UNKNOWN_RESPONSE),
            HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<String> extendLoan(Long productId) {
    Optional<Product> productOptional = productService.extendLoan(productId);
    if (!productOptional.isPresent()) {
      return new ResponseEntity<>(addMetaData(EXTEND_LOAN_FAIL_RESPONSE), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(addMetaData(EXTEND_LOAN_SUCCESS_RESPONSE,
            productOptional.get().getId()), HttpStatus.OK);
  }

  private String addMetaData(String inputMsg) {
    return inputMsg + "\n\n" + addRequestTime();
  }

  private String addMetaData(String inputMsg, Long loanId) {
    return addMetaData(inputMsg) + "\t" + LOAN_ID_METADATA + " " + loanId;
  }

  private String addRequestTime() {
    LocalDateTime applicationTime = LocalDateTime.now();
    return TIME_METADATA + " " + applicationTime.toString();
  }
}
