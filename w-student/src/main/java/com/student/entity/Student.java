package com.student.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "admin_student_seq")
public class Student extends DataObject {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String city;
	private String pinCode;
	private String standard;
	private Double percentageMarks;
	private String email;
	
	public Student() {
		
	}
	
	public Student(Integer id, String name, String city, String pinCode, String standard, Double percentageMarks, String email) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.standard = standard;
		this.percentageMarks = percentageMarks;
		this.email = email;
	}
	
	public Student(String name, String city, String pinCode, String standard, Double percentageMarks, String email) {
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.standard = standard;
		this.percentageMarks = percentageMarks;
		this.email= email;
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
}
