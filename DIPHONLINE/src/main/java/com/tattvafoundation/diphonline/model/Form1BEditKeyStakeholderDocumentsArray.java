package com.tattvafoundation.diphonline.model;

public class Form1BEditKeyStakeholderDocumentsArray {

	private String stakeholder_name;
	private String contact_details;
	private String primary_key;
	
	public Form1BEditKeyStakeholderDocumentsArray() {
		
	}

	public Form1BEditKeyStakeholderDocumentsArray(String stakeholder_name, String contact_details, String primary_key) {
		this.stakeholder_name = stakeholder_name;
		this.contact_details = contact_details;
		this.primary_key = primary_key;
	}

	public String getStakeholder_name() {
		return stakeholder_name;
	}

	public void setStakeholder_name(String stakeholder_name) {
		this.stakeholder_name = stakeholder_name;
	}

	public String getContact_details() {
		return contact_details;
	}

	public void setContact_details(String contact_details) {
		this.contact_details = contact_details;
	}

	public String getPrimary_key() {
		return primary_key;
	}

	public void setPrimary_key(String primary_key) {
		this.primary_key = primary_key;
	}

	@Override
	public String toString() {
		return "Form1BEditKeyStakeholderDocumentsArray [stakeholder_name=" + stakeholder_name + ", contact_details="
				+ contact_details + ", primary_key=" + primary_key + "]";
	}
	
	
}
