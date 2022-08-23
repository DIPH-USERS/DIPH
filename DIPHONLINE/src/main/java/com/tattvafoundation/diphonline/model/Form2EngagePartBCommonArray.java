package com.tattvafoundation.diphonline.model;

public class Form2EngagePartBCommonArray {

	private String document_select_stakeholder;
	private String document_desc;
	private String form_2_section_id;
	
	
	
	public Form2EngagePartBCommonArray() {
		
	}
	
	public Form2EngagePartBCommonArray(String document_select_stakeholder, String document_desc) {
		super();
		this.document_select_stakeholder = document_select_stakeholder;
		this.document_desc = document_desc;
		this.form_2_section_id = null;
	}
	
	

	public Form2EngagePartBCommonArray(String document_select_stakeholder, String document_desc,
			String form_2_section_id) {
		super();
		this.document_select_stakeholder = document_select_stakeholder;
		this.document_desc = document_desc;
		this.form_2_section_id = form_2_section_id;
	}



	public String getDocument_select_stakeholder() {
		return document_select_stakeholder;
	}

	public void setDocument_select_stakeholder(String document_select_stakeholder) {
		this.document_select_stakeholder = document_select_stakeholder;
	}

	public String getDocument_desc() {
		return document_desc;
	}

	public void setDocument_desc(String document_desc) {
		this.document_desc = document_desc;
	}

	public String getForm_2_section_id() {
		return form_2_section_id;
	}

	public void setForm_2_section_id(String form_2_section_id) {
		this.form_2_section_id = form_2_section_id;
	}

	@Override
	public String toString() {
		return "Form2EngagePartBCommonArray [document_select_stakeholder=" + document_select_stakeholder
				+ ", document_desc=" + document_desc + ", form_2_section_id=" + form_2_section_id + "]";
	}
	
	

}
