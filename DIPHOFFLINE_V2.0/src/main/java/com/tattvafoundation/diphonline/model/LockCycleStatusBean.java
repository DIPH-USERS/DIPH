package com.tattvafoundation.diphonline.model;

public class LockCycleStatusBean {

	private String status;
	private String message;
	
	public LockCycleStatusBean() {
		
	}
	
	public LockCycleStatusBean(String status, String message) {		
		this.status = status;
		this.message = message;
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
	@Override
	public String toString() {
		return "LockCycleStatusBean [status=" + status + ", message=" + message + "]";
	}
	
	
	
}
