package com.springboot.eureka.client2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/eureka")
public class Eureka2Resource {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/client2")
	public String getThisClient() {
		return "Eureka Client 2";
	}
	
	@GetMapping("/client1")
	public String getEurekaClient1() {
		//String url = "http://localhost:8761/rest/eureka/client1";
		String url = "http://eureka-client-1/rest/eureka/client1";
		return restTemplate.getForObject(url, String.class);
	}
}
