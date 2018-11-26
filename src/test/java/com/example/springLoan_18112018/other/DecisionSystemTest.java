package com.example.springLoan_18112018.other;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DecisionSystemTest {

    @Autowired
    DecisionSystem decisionSystem;

    @Test
    public void sanityTest(){
        System.out.println("Hello world");
    }
}
