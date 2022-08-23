package com.tattvafoundation.diphonline.model;

public class Form1BSaveKeyNgoDocumentsArray {

	private String ngo_name;
	private String ngo_details;
	
	public Form1BSaveKeyNgoDocumentsArray() {
		
	}

	public Form1BSaveKeyNgoDocumentsArray(String ngo_name, String ngo_details) {
		this.ngo_name = ngo_name;
		this.ngo_details = ngo_details;
	}

	public String getNgo_name() {
		return ngo_name;
	}

	public void setNgo_name(String ngo_name) {
		this.ngo_name = ngo_name;
	}

	public String getNgo_details() {
		return ngo_details;
	}

	public void setNgo_details(String ngo_details) {
		this.ngo_details = ngo_details;
	}

	@Override
	public String toString() {
		return "Form1BSaveKeyNgoDocumentsArray [ngo_name=" + ngo_name + ", ngo_details=" + ngo_details + "]";
	}
}
