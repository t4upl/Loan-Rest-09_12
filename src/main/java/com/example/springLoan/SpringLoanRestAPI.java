package com.example.springLoan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringLoanRestAPI {

	public static void main(String[] args) {
		SpringApplication.run(SpringLoanRestAPI.class, args);
	}
}
