package com.bankingmanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCaching
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class BankingMgmtApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankingMgmtApiApplication.class, args);
		log.info("Banking Management application has been started");
	}
}
