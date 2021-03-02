package com.oneworldaccuracy.onboardingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com")
@EnableAsync
public class OnboardingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingServiceApplication.class, args);
	}

}
