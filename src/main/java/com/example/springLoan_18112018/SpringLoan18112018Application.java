package com.example.springLoan_18112018;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class SpringLoan18112018Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringLoan18112018Application.class, args);
	}
}
