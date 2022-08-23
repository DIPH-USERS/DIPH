package com.tattvafoundation.diphonline.model;

public class Form2EngagePartACommonStakeHoldersArray {

	private String document_label;
	private String document_name;
	private String document_id;
	
	public Form2EngagePartACommonStakeHoldersArray() {
		
	}
	
	public Form2EngagePartACommonStakeHoldersArray(String document_name) {
		this.document_label = null;
		this.document_name = document_name;
		this.document_id = null;
	}

	public Form2EngagePartACommonStakeHoldersArray(String document_label, String document_name, String document_id) {
		this.document_label = document_label;
		this.document_name = document_name;
		this.document_id = document_id;
	}

	public String getDocument_label() {
		return document_label;
	}

	public void setDocument_label(String document_label) {
		this.document_label = document_label;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public String getDocument_id() {
		return document_id;
	}

	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}

	@Override
	public String toString() {
		return "Form2EngagePartACommonStakeHoldersArray [document_label=" + document_label + ", document_name="
				+ document_name + ", document_id=" + document_id + "]";
	}
	
	
}
