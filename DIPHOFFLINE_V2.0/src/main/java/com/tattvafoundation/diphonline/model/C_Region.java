package com.tattvafoundation.diphonline.model;

import java.util.List;

public class C_Region {

	private List<Regions> result;
	private String error_code;
	private String message;
	
	public C_Region() {
		
	}

	public C_Region(List<Regions> result, String error_code, String message) {
		this.result = result;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Regions> getResult() {
		return result;
	}

	public void setResult(List<Regions> result) {
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
		return "C_Region [result=" + result + ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
	
}
