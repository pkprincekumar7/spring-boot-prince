package com.health.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.model.HealthResponse;
import com.health.model.User;

@Service
public class HealthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HealthService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HealthNotificationService healthNotificationService;
	
	public HealthResponse getHealth(String module) {
		String url = "http://" + module + "/health";
		HealthResponse healthResponse = null;
		try {
			String responseString = restTemplate.getForObject(url, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			healthResponse = objectMapper.readValue(responseString, HealthResponse.class);
			healthResponse.setApplicationName(module.toUpperCase());
		} catch (Exception e) {
			try {
				healthNotificationService.sendNotification(this.getUserInfo(module.toUpperCase()));
			} catch (MailException e1) {
				LOGGER.error(e1.getMessage());
			} catch (Exception e1) {
				LOGGER.error(e1.getMessage());
			}
			healthResponse = this.getDownStatus(module.toUpperCase());
			LOGGER.error(e.getMessage());
		}
		LOGGER.info(healthResponse.toString());
		return healthResponse;
	}
	
	public User getUserInfo(String module) {
		User user = new User();
		user.setName("Prince Kumar");
		user.setEmail("princekumar@triconinfotech.com");
		user.setSubject("Health of " + module + " module is DOWN");
		user.setMessageBody("Health of " + module + " module is DOWN");
		return user;
	}
	
	public HealthResponse getDownStatus(String module) {
		HealthResponse healthResponse = new HealthResponse();
		healthResponse.setApplicationName(module);
		healthResponse.setStatus("DOWN");
		return healthResponse;
	}
}
