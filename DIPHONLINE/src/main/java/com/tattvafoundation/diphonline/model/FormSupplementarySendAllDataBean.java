package com.tattvafoundation.diphonline.model;

import java.util.List;

public class FormSupplementarySendAllDataBean {
	
	private List<FormSupplementaryAPrimaryTableDataBean> extractdata;
	private List<FormSupplementaryAActionTableDataBean> extractdataaction;
	private String error_code;
	private String message;
	
	public FormSupplementarySendAllDataBean() {
	}

	public FormSupplementarySendAllDataBean(List<FormSupplementaryAPrimaryTableDataBean> extractdata,
			List<FormSupplementaryAActionTableDataBean> extractdataaction, String error_code, String message) {
		
		this.extractdata = extractdata;
		this.extractdataaction = extractdataaction;
		this.error_code = error_code;
		this.message = message;
	}

	public List<FormSupplementaryAPrimaryTableDataBean> getExtractdata() {
		return extractdata;
	}

	public void setExtractdata(List<FormSupplementaryAPrimaryTableDataBean> extractdata) {
		this.extractdata = extractdata;
	}

	public List<FormSupplementaryAActionTableDataBean> getExtractdataaction() {
		return extractdataaction;
	}

	public void setExtractdataaction(List<FormSupplementaryAActionTableDataBean> extractdataaction) {
		this.extractdataaction = extractdataaction;
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
		return "FormSupplementarySendAllDataBean [extractdata=" + extractdata + ", extractdataaction="
				+ extractdataaction + ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
}
