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

import com.admin.model.CollegeModel;
import com.admin.service.CollegeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "College Resource", description = "Api's related to College")
@RequestMapping("/college")
public class CollegeController {
	
	@Autowired
	CollegeService collegeService;
	
	@ApiOperation(value = "Get All Colleges",
			notes = "Get all colleges")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<CollegeModel> getAllColleges() {
		return collegeService.getAllColleges();
	}
	
	
	@ApiOperation(value = "Get College",
			notes = "Get college based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public CollegeModel getCollege(@PathVariable("id") Integer id) {
		return collegeService.getCollege(id);
	}
	
	
	@ApiOperation(value = "Add College",
			notes = "Add college")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public String addCollege(@RequestBody CollegeModel college) {
		return collegeService.addCollege(college);
	}
	
	
	@ApiOperation(value = "Update College",
			notes = "Update college based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PutMapping("/{id}")
	public String updateCollege(@PathVariable("id") Integer id, @RequestBody CollegeModel college) {
		return collegeService.updateCollege(id, college);
	}
	
	
	@ApiOperation(value = "Delete College",
			notes = "Delete college based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@DeleteMapping("/{id}")
	public String deleteCollege(@PathVariable("id") Integer id) {
		return collegeService.deleteCollege(id);
	}
}
