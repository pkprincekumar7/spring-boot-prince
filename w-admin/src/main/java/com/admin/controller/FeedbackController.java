package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.FeedbackModel;
import com.admin.service.FeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Feedback Resource", description = "Api's related to Feedback")
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackService feedbackService;

	@ApiOperation(value = "Get All Feedbacks",
			notes = "Get all feedbacks")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping
	public List<FeedbackModel> getAllFeedbacks() {
		return feedbackService.getAllFeedbacks();
	}
	
	
	@ApiOperation(value = "Get Feedback",
			notes = "Get feedback based on id")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@GetMapping("/{id}")
	public FeedbackModel getFeedback(@PathVariable("id") Integer id) {
		return feedbackService.getFeedback(id);
	}
	
	
	@ApiOperation(value = "Add Feedback",
			notes = "Add feedback")
	@ApiResponses(value = {
		@ApiResponse(code = 100, message = "100 is the message"),
		@ApiResponse(code = 200, message = "Successful")
	})
	@PostMapping()
	public String addFeedback(@RequestBody FeedbackModel feedback) {
		return feedbackService.addFeedback(feedback);
	}
}
