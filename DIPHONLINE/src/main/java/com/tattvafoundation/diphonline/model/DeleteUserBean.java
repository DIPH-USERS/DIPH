package com.tattvafoundation.diphonline.model;

public class DeleteUserBean {

	private String username;
	private String loggedUser;
	
	public DeleteUserBean() {
		
	}

	public DeleteUserBean(String username, String loggedUser) {
		this.username = username;
		this.loggedUser = loggedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	@Override
	public String toString() {
		return "DeleteUserBean [username=" + username + ", loggedUser=" + loggedUser + "]";
	}
	
	
}
