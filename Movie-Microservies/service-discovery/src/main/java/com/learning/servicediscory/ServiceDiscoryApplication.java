package com.learning.servicediscory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoryApplication.class, args);
	}

}
