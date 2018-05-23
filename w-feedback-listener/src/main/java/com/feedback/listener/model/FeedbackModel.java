package com.feedback.listener.model;

public class FeedbackModel {

	private Integer id;
	private Integer candidateId;
	private String message;
	private String email;
	private String action;
	
	public FeedbackModel() {
		
	}
	
	public FeedbackModel(Integer id, Integer candidateId, String message, String email) {
		this.id = id;
		this.candidateId = candidateId;
		this.message = message;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
