package com.tattvafoundation.diphonline.model;

public class DistrictResponseBean {

	private String status;
	private String message;
	
	public DistrictResponseBean() {
		
	}
	
	public DistrictResponseBean(String status, String message) {
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
		return "DeleteDistrictResponseBean [status=" + status + ", message=" + message + "]";
	}

	
	
}
