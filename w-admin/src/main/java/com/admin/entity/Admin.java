package com.admin.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "admin_admin_seq")
public class Admin extends DataObject {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String city;
	private String pinCode;
	private String designation;
	private String email;
	
	public Admin() {
		
	}
	
	public Admin(Integer id, String name, String city, String pinCode, String designation, String email) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.designation = designation;
		this.email = email;
	}
	
	public Admin(String name, String city, String pinCode, String designation, String email) {
		this.name = name;
		this.city = city;
		this.pinCode = pinCode;
		this.designation = designation;
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
}
