package com.health.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.email.service.HealthService;
import com.health.model.HealthResponse;

@RestController
@RequestMapping("/health")
public class HealthController {

	@Autowired
	private HealthService healthService;
	
	@GetMapping("/login")
	public HealthResponse getLoginHealth() {
		return healthService.getHealth("w-login");
	}
	
	@GetMapping("/admin")
	public HealthResponse getAdminHealth() {
		return healthService.getHealth("w-admin");
	}
	
	@GetMapping("/teacher")
	public HealthResponse getTeacherHealth() {
		return healthService.getHealth("w-teacher");
	}
	
	@GetMapping("/student")
	public HealthResponse getStudentHealth() {
		return healthService.getHealth("w-student");
	}
	
	@GetMapping("/all")
	public List<HealthResponse> getHealthOfAll() {
		List<HealthResponse> healthList = new ArrayList<>();
		healthList.add(healthService.getHealth("w-login"));
		healthList.add(healthService.getHealth("w-admin"));
		healthList.add(healthService.getHealth("w-teacher"));
		healthList.add(healthService.getHealth("w-student"));
		return healthList;
	}
}
