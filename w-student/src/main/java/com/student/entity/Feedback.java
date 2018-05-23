package com.student.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "admin_feedback_seq")
public class Feedback extends DataObject {

	private static final long serialVersionUID = 1L;
	
	private Integer candidateId;
	private String message;
	private String email;
	
	public Feedback() {
		
	}
	
	public Feedback(Integer id, Integer candidateId, String message, String email) {
		this.id = id;
		this.candidateId = candidateId;
		this.message = message;
		this.email = email;
	}
	
	public Feedback(Integer candidateId, String message, String email) {
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
}
