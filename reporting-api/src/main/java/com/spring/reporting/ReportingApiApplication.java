package com.spring.reporting;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class ReportingApiApplication {




	public static void main(String[] args) {
		log.info("Initializing Reporting API application");
		SpringApplication.run(ReportingApiApplication.class, args);
	}

}
