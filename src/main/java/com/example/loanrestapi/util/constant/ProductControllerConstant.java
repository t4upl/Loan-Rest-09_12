package com.example.loanrestapi.util.constant;

public class ProductControllerConstant {

  public static final String SANITY_TEST_PATH = "/sanity-test";
  public static final String GET_PRODUCT_PATH = "/products";
  public static final String PATCH_PRODUCT_PATH = "/products/{id}";

  public static final String SANITY_TEST_RESPONSE = "Sanity test passed";
  public static final String LOAN_ID_METADATA = "Loan Id:";
  public static final String TIME_METADATA = "Time:";

  public static final String APPLY_FOR_LOAN_SUCCESS_RESPONSE = "Loan successfully applied.";
  public static final String APPLY_FOR_LOAN_FAIL_RESPONSE = "Loan refused.";
  public static final String EXTEND_LOAN_SUCCESS_RESPONSE = "Loan successfully extended.";
  public static final String EXTEND_LOAN_FAIL_RESPONSE = "Loan extension failed.";
  public static final String PATCH_PRODUCT_ACTION_UNKNOWN_RESPONSE = "Action not supported.";

  public static final String ACTION = "action";
  public static final String EXTEND_LOAN_ACTION = "extend-loan";


}
