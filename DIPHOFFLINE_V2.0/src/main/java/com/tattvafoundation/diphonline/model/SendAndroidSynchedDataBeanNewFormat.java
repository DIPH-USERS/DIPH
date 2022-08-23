package com.tattvafoundation.diphonline.model;

import java.util.List;

public class SendAndroidSynchedDataBeanNewFormat {

	private List<Form1APrimaryTableDataBean> formA;
	private List<Form1ADocumentsTableDataBean> formAdocument;
	private String error_code;
	private String message;
	
	public SendAndroidSynchedDataBeanNewFormat() {
		
	}
	
	
	public SendAndroidSynchedDataBeanNewFormat(List<Form1APrimaryTableDataBean> formA,
			List<Form1ADocumentsTableDataBean> formAdocument, String error_code, String message) {
		this.formA = formA;
		this.formAdocument = formAdocument;
		this.error_code = error_code;
		this.message = message;
	}


	public List<Form1APrimaryTableDataBean> getFormA() {
		return formA;
	}


	public void setFormA(List<Form1APrimaryTableDataBean> formA) {
		this.formA = formA;
	}


	public List<Form1ADocumentsTableDataBean> getFormAdocument() {
		return formAdocument;
	}


	public void setFormAdocument(List<Form1ADocumentsTableDataBean> formAdocument) {
		this.formAdocument = formAdocument;
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
		return "SendAndroidSynchedDataBeanNewFormat [formA=" + formA + ", formAdocument=" + formAdocument
				+ ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
	
}
