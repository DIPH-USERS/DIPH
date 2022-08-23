package com.tattvafoundation.diphonline.model;

import java.util.List;



public class Countries {

	private List<Country> result;
	private String error_code;
	private String message;
	
	public Countries() {
		
	}

	public Countries(List<Country> result, String error_code, String message) {
		super();
		this.result = result;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Country> getResult() {
		return result;
	}

	public void setResult(List<Country> result) {
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
		return "Countries [result=" + result + ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
	
	
}
