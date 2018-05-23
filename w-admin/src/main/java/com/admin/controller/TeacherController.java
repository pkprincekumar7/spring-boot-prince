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

import com.admin.model.TeacherModel;
import com.admin.service.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Teacher Resource", description = "Api's related to Teacher")
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;
	
	@ApiOperation(value = "Get All Teachers",
			notes = "Get all teachers")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<TeacherModel> getAllTeachers() {
		return teacherService.getAllTeachers();
	}
	
	
	@ApiOperation(value = "Get Teacher",
			notes = "Get teacher based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public TeacherModel getTeacher(@PathVariable("id") Integer id) {
		return teacherService.getTeacher(id);
	}
	
	
	@ApiOperation(value = "Add Teacher",
			notes = "Add teacher")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public String addTeacher(@RequestBody TeacherModel teacher) {
		return teacherService.addTeacher(teacher);
	}
	
	
	@ApiOperation(value = "Update Teacher",
			notes = "Update teacher based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PutMapping("/{id}")
	public String updateTeacher(@PathVariable("id") Integer id, @RequestBody TeacherModel teacher) {
		return teacherService.updateTeacher(id, teacher);
	}
	
	
	@ApiOperation(value = "Delete Teacher",
			notes = "Delete teacher based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@DeleteMapping("/{id}")
	public String deleteTeacher(@PathVariable("id") Integer id) {
		return teacherService.deleteTeacher(id);
	}
}
