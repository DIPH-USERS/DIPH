package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form1ASendAllDataToAndroidBean {

	
	List<Form1APrimaryTableDataBean> formA;
	List<Form1ADocumentsTableDataBean> formAdocument;
	List<Form1ASourceIDDetailsBean> mapping;
	private String error_code;
	private String message; 
	
	public Form1ASendAllDataToAndroidBean() {
	}

	public Form1ASendAllDataToAndroidBean(List<Form1APrimaryTableDataBean> formA,
			List<Form1ADocumentsTableDataBean> formAdocument, List<Form1ASourceIDDetailsBean> mapping,
			String error_code, String message) {
		this.formA = formA;
		this.formAdocument = formAdocument;
		this.mapping = mapping;
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

	public List<Form1ASourceIDDetailsBean> getMapping() {
		return mapping;
	}

	public void setMapping(List<Form1ASourceIDDetailsBean> mapping) {
		this.mapping = mapping;
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
		return "Form1ASendAllDataToAndroidBean [formA=" + formA + ", formAdocument=" + formAdocument + ", mapping="
				+ mapping + ", error_code=" + error_code + ", message=" + message + "]";
	}

	
}
