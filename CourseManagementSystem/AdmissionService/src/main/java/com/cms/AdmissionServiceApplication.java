package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "com.cms.model", "com.cms.*", "com.cms.service", "com.cms.exception",
		"com.cms.validation", "com.*", "com.cms.proxy", "com.cms.service.*" })
@EnableMongoRepositories("com.cms.repository")
@EnableFeignClients
@EnableRetry
public class AdmissionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmissionServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
