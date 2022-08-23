package com.tattvafoundation.diphonline.model;

import java.util.List;

public class User_Info {	
	

	private String loggedUser;
	private String loggedUserDistrict;
	private String username;
	private String user_password;
	private String email;
	private String country;
	private String region;
	private String state;
	private List<String>  district;
	private List<String> cycle;
	private List<String> year;
	
	private String createUser;	
	private String editUser;
	private String viewUser;
	private String deleteUser;
	private String userStatus;

	public User_Info() {

	}

	public User_Info(String loggedUser, String loggedUserDistrict, String username, String user_password, String email,
			String country, String region, String state, List<String> district, List<String> cycle, List<String> year,
			String createUser, String editUser, String viewUser, String deleteUser, String userStatus) {
		
		this.loggedUser = loggedUser;
		this.loggedUserDistrict = loggedUserDistrict;
		this.username = username;
		this.user_password = user_password;
		this.email = email;
		this.country = country;
		this.region = region;
		this.state = state;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.createUser = createUser;
		this.editUser = editUser;
		this.viewUser = viewUser;
		this.deleteUser = deleteUser;
		this.userStatus = userStatus;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<String> getDistrict() {
		return district;
	}

	public void setDistrict(List<String> district) {
		this.district = district;
	}

	public List<String> getCycle() {
		return cycle;
	}

	public void setCycle(List<String> cycle) {
		this.cycle = cycle;
	}

	public List<String> getYear() {
		return year;
	}

	public void setYear(List<String> year) {
		this.year = year;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEditUser() {
		return editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

	public String getViewUser() {
		return viewUser;
	}

	public void setViewUser(String viewUser) {
		this.viewUser = viewUser;
	}

	public String getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "User_Info [loggedUser=" + loggedUser + ", loggedUserDistrict=" + loggedUserDistrict + ", username="
				+ username + ", user_password=" + user_password + ", email=" + email + ", country=" + country
				+ ", region=" + region + ", state=" + state + ", district=" + district + ", cycle=" + cycle + ", year="
				+ year + ", createUser=" + createUser + ", editUser=" + editUser + ", viewUser=" + viewUser
				+ ", deleteUser=" + deleteUser + ", userStatus=" + userStatus + "]";
	}

	
}
