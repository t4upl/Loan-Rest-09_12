package com.example.springLoan_18112018;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping(value = "/test")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>("Test passed", HttpStatus.OK);
    }
}
