package com.tattvafoundation.diphonline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Login {

	
	@JsonIgnore
	private String password;
	private String username;
	private String status;
	private String userStatus;
	private int id;
	
	public Login() {
		
	}

	public Login(String password, String username, String status, String userStatus, int id) {		
		this.password = password;
		this.username = username;
		this.status = status;
		this.userStatus = userStatus;
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Login [password=" + password + ", username=" + username + ", status=" + status + ", userStatus="
				+ userStatus + ", id=" + id + "]";
	}

	
}
