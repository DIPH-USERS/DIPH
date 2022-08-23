package com.tattvafoundation.diphonline.model;

public class Form1BSaveKeyStakeholderDocumentsArray {

	private String stakeholder_name;
	private String contact_details;
	
	public Form1BSaveKeyStakeholderDocumentsArray() {
	}
	
	public Form1BSaveKeyStakeholderDocumentsArray(String stakeholder_name, String contact_details) {
		this.stakeholder_name = stakeholder_name;
		this.contact_details = contact_details;
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

	@Override
	public String toString() {
		return "Form1BSaveKeyStakeholderDocumentsArray [stakeholder_name=" + stakeholder_name + ", contact_details="
				+ contact_details + "]";
	}
	
}
