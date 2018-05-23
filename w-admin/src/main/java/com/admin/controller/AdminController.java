package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.AdminModel;
import com.admin.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Admin Resource", description = "Api's related to Admin")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@ApiOperation(value = "Get All Admins",
			notes = "Get all admins")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<AdminModel> getAllAdmins() {
		return adminService.getAllAdmins();
	}
	
	
	@ApiOperation(value = "Get Admin",
			notes = "Get admin based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public AdminModel getAdmin(@PathVariable("id") Integer id) {
		return adminService.getAdmin(id);
	}
	
	
	@ApiOperation(value = "Add Admin",
			notes = "Add admin")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public String addAdmin(@RequestBody AdminModel admin) {
		return adminService.addAdmin(admin);
	}
	
	
	@ApiOperation(value = "Update Admin",
			notes = "Update admin based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PutMapping("/{id}")
	public String updateAdmin(@PathVariable("id") Integer id, @RequestBody AdminModel admin) {
		return adminService.updateAdmin(id, admin);
	}
	
	
	@ApiOperation(value = "Delete Admin",
			notes = "Delete admin based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@DeleteMapping("/{id}")
	public String deleteAdmin(@PathVariable("id") Integer id) {
		return adminService.deleteAdmin(id);
	}
}
