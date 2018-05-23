package com.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(WLoginApplication.class, args);
	}
}
