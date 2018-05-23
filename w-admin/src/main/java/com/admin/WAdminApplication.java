package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(WAdminApplication.class, args);
	}
}
