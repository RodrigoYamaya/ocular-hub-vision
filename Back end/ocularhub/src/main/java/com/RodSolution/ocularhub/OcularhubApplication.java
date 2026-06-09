package com.RodSolution.ocularhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OcularhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcularhubApplication.class, args);
	}

}
