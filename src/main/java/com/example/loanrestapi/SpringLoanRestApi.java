package com.example.loanrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringLoanRestApi {

  public static void main(String[] args) {
    SpringApplication.run(SpringLoanRestApi.class, args);
  }
}
