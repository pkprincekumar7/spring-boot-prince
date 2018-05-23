package com.springboot.eureka.client1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/eureka")
public class Eureka1Resource {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/client1")
	public String getThisClient() {
		return "Eureka Client 1";
	}
	
	@GetMapping("/client2")
	public String getEurekaClient2() {
		//String url = "http://localhost:8761/rest/eureka/client2";
		String url = "http://eureka-client-2/rest/eureka/client2";
		return restTemplate.getForObject(url, String.class);
	}
}
