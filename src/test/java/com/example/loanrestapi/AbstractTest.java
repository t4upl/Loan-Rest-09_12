package com.example.loanrestapi;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public abstract class AbstractTest {

    @Test
    public void smokeTest(){
        System.out.println("smokeTest passed.");
    }

    //stric mocking class
    @AllArgsConstructor
    @NoArgsConstructor
    protected static class NullPointerExceptionAnswer<T> implements Answer<T> {
        private static final String STRICT_MOCKING_ERROR = "Strict mocking error: ";
        private String message = "no message provided.";

        @Override
        public T answer(InvocationOnMock invocation) throws Throwable {
            throw new NullPointerException(STRICT_MOCKING_ERROR + this.message);
        }
    }

}
