package com.login.model;

import io.swagger.annotations.ApiModelProperty;

public class Login {

	@ApiModelProperty(notes = "Id of the user")
	private Integer id;
	
	@ApiModelProperty(notes = "Password of the user")
	private String password;
	
	@ApiModelProperty(notes = "Role of the user")
	private String role;
	
	@ApiModelProperty(notes = "Auth key of the user")
	private String authKey;
	
	public Login() {
		
	}
	
	public Login(Integer id, String password, String role, String authKey) {
		this.id = id;
		this.password = password;
		this.role = role;
		this.authKey = authKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
}
