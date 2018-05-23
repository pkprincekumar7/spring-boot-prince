package com.springboot.swagger2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Swagger2ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Swagger2ExampleApplication.class, args);
	}
}
