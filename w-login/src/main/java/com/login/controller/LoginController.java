package com.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.model.Login;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Login Resource", description = "Api's related to Login")
@RequestMapping("/login")
public class LoginController {

	@ApiOperation(value = "Get Login Details Of All Users",
			notes = "Get login details of all users")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<Login> getAllLogins() {
		List<Login> loginList = new ArrayList<>();
		loginList.add(new Login(1001, "Pass@123", "Admin", "9999999999"));
		return loginList;
	}
	
	
	@ApiOperation(value = "Get Login Details Of a User",
			notes = "Get login details of a user")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public Login getLogin(@PathVariable("id") Integer id) {
		return new Login(id, "Pass@123", "Admin", "9999999999");
	}
	
	
	@ApiOperation(value = "Login using loginId and password",
			notes = "Login using loginId and password")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public Login login(@RequestBody Login login) {
		// return login Id, role and auth key after successful authentication
		login.setPassword(null);
		return login;
	}
}
