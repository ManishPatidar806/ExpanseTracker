package com.financialapplication.expansesanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExpansesAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpansesAnalysisApplication.class, args);
	}

}
