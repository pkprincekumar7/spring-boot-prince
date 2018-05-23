package com.student.listener.model;

public class StudentModel {

	private Integer id;
	private String name;
	private String city;
	private String pinCode;
	private String standard;
	private Double percentageMarks;
	private String email;
	private String action;
	
	public StudentModel() {
		
	}
	
	public StudentModel(Integer id, String name, String city, String pinCode, String standard, Double percentageMarks, String email, String action) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.standard = standard;
		this.percentageMarks = percentageMarks;
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
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Double getPercentageMarks() {
		return percentageMarks;
	}
	public void setPercentageMarks(Double percentageMarks) {
		this.percentageMarks = percentageMarks;
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
