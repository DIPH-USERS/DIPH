package com.tattvafoundation.diphonline.model;



public class SendAndroidSynchedDataBean {

	private Form1ASendAllDataToAndroidBean result;
	private String error_code;
	private String message;
	
	public SendAndroidSynchedDataBean() {
		
	}

	public SendAndroidSynchedDataBean(Form1ASendAllDataToAndroidBean result, String error_code, String message) {
		this.result = result;
		this.error_code = error_code;
		this.message = message;
	}

	public Form1ASendAllDataToAndroidBean getResult() {
		return result;
	}

	public void setResult(Form1ASendAllDataToAndroidBean result) {
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
		return "SendAndroidSynchedDataBean [result=" + result + ", error_code=" + error_code + ", message=" + message
				+ "]";
	}
	
	
}
