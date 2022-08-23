package com.tattvafoundation.diphonline.model;

public class APPLoginPostParameterBean {

	private String username;
	private String password;
	
	public APPLoginPostParameterBean() {
		
	}
	
	public APPLoginPostParameterBean(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "APPLoginPostParameterBean [username=" + username + ", password=" + password + "]";
	}
	
	
	
}
