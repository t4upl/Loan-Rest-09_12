package com.example.springLoan.util.constant;

public class ProductControllerConstant {
    public static final String SANITY_TEST_PATH = "/sanity-test";
    public static final String APPLY_FOR_LOAN_PATH = "/products";
    public static final String EXTEND_LOAN_PATH = "/products/{id}?action=extend";

    public static final String SANITY_TEST_RESPONSE = "Sanity test passed";
    public static final String APPLY_FOR_LOAN_SUCCESS_RESPONSE = "Loan successfully applied.";
    public static final String APPLY_FOR_LOAN_FAIL_RESPONSE = "Loan refused.";
    public static final String LOAN_ID_METADATA = "Loan Id:";
    public static final String TIME_METADATA = "Time:";
}
