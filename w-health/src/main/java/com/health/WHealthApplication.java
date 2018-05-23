package com.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(WHealthApplication.class, args);
	}
}
