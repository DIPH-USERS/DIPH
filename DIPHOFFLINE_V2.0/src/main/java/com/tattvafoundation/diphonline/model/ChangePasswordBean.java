package com.tattvafoundation.diphonline.model;

public class ChangePasswordBean {

	private String confirm_password; 
	private String new_password;
	private String old_password;
	private String username;
	private String result;
	private String message;
	
	public ChangePasswordBean() {
		
	}

	public ChangePasswordBean(String confirm_password, String new_password, String old_password, String username,
			String result, String message) {
		
		this.confirm_password = confirm_password;
		this.new_password = new_password;
		this.old_password = old_password;
		this.username = username;
		this.result = result;
		this.message = message;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChangePasswordBean [confirm_password=" + confirm_password + ", new_password=" + new_password
				+ ", old_password=" + old_password + ", username=" + username + ", result=" + result + ", message="
				+ message + "]";
	}

	
}
