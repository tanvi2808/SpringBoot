package com.spring.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestReportingApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(ReportingApiApplication::main).with(TestReportingApiApplication.class).run(args);
	}

}
