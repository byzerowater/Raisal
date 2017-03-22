package com.fourground.raisal.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages = {
			"com.fourground.raisal"
	}
)
public class AppraisalServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppraisalServerApplication.class, args);
	}
}
