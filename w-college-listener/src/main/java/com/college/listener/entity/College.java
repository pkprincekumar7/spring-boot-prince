package com.college.listener.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "admin_college_seq")
public class College extends DataObject {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String city;
	private String pinCode;
	private String minStandard;
	private String maxStandard;
	private String email;
	
	public College() {
		
	}
	
	public College(Integer id, String name, String city, String pinCode, String minStandard, String maxStandard, String email) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.minStandard = minStandard;
		this.maxStandard = maxStandard;
		this.email = email;
	}
	
	public College(String name, String city, String pinCode, String minStandard, String maxStandard, String email) {
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.minStandard = minStandard;
		this.maxStandard = maxStandard;
		this.email = email;
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
	public String getMinStandard() {
		return minStandard;
	}
	public void setMinStandard(String minStandard) {
		this.minStandard = minStandard;
	}
	public String getMaxStandard() {
		return maxStandard;
	}
	public void setMaxStandard(String maxStandard) {
		this.maxStandard = maxStandard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
