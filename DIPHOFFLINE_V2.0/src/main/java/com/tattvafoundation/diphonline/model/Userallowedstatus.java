package com.tattvafoundation.diphonline.model;

public class Userallowedstatus {

	private String allowed;
	private String message;
	
	public Userallowedstatus() {
	}
	
	public Userallowedstatus(String allowed, String message) {
		this.allowed = allowed;
		this.message = message;
	}

	public String getAllowed() {
		return allowed;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Userallowedstatus [allowed=" + allowed + ", message=" + message + "]";
	}
	
	
}
