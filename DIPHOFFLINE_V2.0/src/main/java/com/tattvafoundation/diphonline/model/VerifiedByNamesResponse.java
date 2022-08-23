package com.tattvafoundation.diphonline.model;

import java.util.List;

public class VerifiedByNamesResponse {

	private String status;
	private String message;
	private List<VerifiedByNameBean> verified_by_name;
	
	public VerifiedByNamesResponse() {
		
	}

	public VerifiedByNamesResponse(String status, String message, List<VerifiedByNameBean> verified_by_name) {
		this.status = status;
		this.message = message;
		this.verified_by_name = verified_by_name;
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

	public List<VerifiedByNameBean> getVerified_by_name() {
		return verified_by_name;
	}

	public void setVerified_by_name(List<VerifiedByNameBean> verified_by_name) {
		this.verified_by_name = verified_by_name;
	}

	@Override
	public String toString() {
		return "VerifiedByNamesResponse [status=" + status + ", message=" + message + ", verified_by_name="
				+ verified_by_name + "]";
	}
	
	
}
