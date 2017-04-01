package com.fourground.raisal.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
	scanBasePackages = {
			"com.fourground.raisal"
	}
)

@EnableScheduling
@EnableAsync
public class AppraisalServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppraisalServerApplication.class, args);
	}
}
