package com.spring.reporting;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
public class ReportingApiApplication {




	public static void main(String[] args) {
		log.info("Initializing Reporting API application");
		SpringApplication.run(ReportingApiApplication.class, args);
	}

}
