package com.tattvafoundation.diphonline.model;

public class ViewUsers_UserBean {
	private String loggedUser;
	private String loggedUserDistrict;
	
	public ViewUsers_UserBean() {
		
	}
	
	public ViewUsers_UserBean(String loggedUser, String loggedUserDistrict) {
		this.loggedUser = loggedUser;
		this.loggedUserDistrict = loggedUserDistrict;
	}
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public String getLoggedUserDistrict() {
		return loggedUserDistrict;
	}
	public void setLoggedUserDistrict(String loggedUserDistrict) {
		this.loggedUserDistrict = loggedUserDistrict;
	}
	@Override
	public String toString() {
		return "ViewUsers_UserBean [loggedUser=" + loggedUser + ", loggedUserDistrict=" + loggedUserDistrict + "]";
	}
	
	
}
