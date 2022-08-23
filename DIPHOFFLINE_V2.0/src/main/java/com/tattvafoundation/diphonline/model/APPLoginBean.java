package com.tattvafoundation.diphonline.model;

import java.util.List;

public class APPLoginBean {

	private List<Login> result;
	private String error_code;
	private String message;
	
	public APPLoginBean() {
		
	}

	public APPLoginBean(List<Login> result, String error_code, String message) {
		this.result = result;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Login> getResult() {
		return result;
	}

	public void setResult(List<Login> result) {
		this.result = result;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "APPLoginBean [result=" + result + ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
}
