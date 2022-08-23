package com.tattvafoundation.diphonline.model;

import java.util.List;


public class ViewExistingUsers {

	private String status;
	private String message;
	private List<AllUsersBean> userList;
	
	public ViewExistingUsers() {
		
	}

	public ViewExistingUsers(String status, String message, List<AllUsersBean> userList) {
		this.status = status;
		this.message = message;
		this.userList = userList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AllUsersBean> getUserList() {
		return userList;
	}

	public void setUserList(List<AllUsersBean> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "ViewExistingUsers [status=" + status + ", message=" + message + ", userList=" + userList + "]";
	}
	
	
	
	
	
}
