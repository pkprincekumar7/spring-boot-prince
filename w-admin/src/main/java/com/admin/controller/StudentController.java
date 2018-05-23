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

import com.admin.model.StudentModel;
import com.admin.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Student Resource", description = "Api's related to Student")
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@ApiOperation(value = "Get All Students",
			notes = "Get all students")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<StudentModel> getAllStudents() {
		return studentService.getAllStudents();
	}
	
	
	@ApiOperation(value = "Get Student",
			notes = "Get student based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public StudentModel getStudent(@PathVariable("id") Integer id) {
		return studentService.getStudent(id);
	}
	
	
	@ApiOperation(value = "Add Student",
			notes = "Add student")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public String addStudent(@RequestBody StudentModel student) {
		return studentService.addStudent(student);
	}
	
	
	@ApiOperation(value = "Update Student",
			notes = "Update student based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PutMapping("/{id}")
	public String updateStudent(@PathVariable("id") Integer id, @RequestBody StudentModel student) {
		return studentService.updateStudent(id, student);
	}
	
	
	@ApiOperation(value = "Delete Student",
			notes = "Delete student based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@DeleteMapping("/{id}")
	public String deleteStudent(@PathVariable("id") Integer id) {
		return studentService.deleteStudent(id);
	}
}
