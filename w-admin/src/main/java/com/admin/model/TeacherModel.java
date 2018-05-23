package com.admin.model;

import io.swagger.annotations.ApiModelProperty;

public class TeacherModel {

	@ApiModelProperty(notes = "Id of the teacher")
	private Integer id;
	
	@ApiModelProperty(notes = "Name of the teacher")
	private String name;
	
	@ApiModelProperty(notes = "City of the teacher")
	private String city;
	
	@ApiModelProperty(notes = "Pincode of the teacher")
	private String pinCode;
	
	@ApiModelProperty(notes = "Designation of the teacher")
	private String designation;
	
	@ApiModelProperty(notes = "Email of the teacher")
	private String email;
	
	@ApiModelProperty(notes = "Action of the teacher")
	private String action;
	
	public TeacherModel() {
		
	}
	
	public TeacherModel(Integer id, String name, String city, String pinCode, String designation, String email, String action) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.designation = designation;
		this.email = email;
		this.action = action;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
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
