package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form2EnagageSendAllDataBean {

	private List<Form2EnagagePrimaryTableDataBean> form2Engage;
	private List<Form2EnagageStakeholdersTableDataBean> form2stakeholders;
	private List<Form2EnagageSectiondetails> form2docs;
	private List<Form2EngagestakeIDDetailsBean> mapping;
	private String error_code;
	private String message;
	
	public Form2EnagageSendAllDataBean() {
		
	}

	public Form2EnagageSendAllDataBean(List<Form2EnagagePrimaryTableDataBean> form2Engage,
			List<Form2EnagageStakeholdersTableDataBean> form2stakeholders, List<Form2EnagageSectiondetails> form2docs,
			List<Form2EngagestakeIDDetailsBean> mapping, String error_code, String message) {
		
		this.form2Engage = form2Engage;
		this.form2stakeholders = form2stakeholders;
		this.form2docs = form2docs;
		this.mapping = mapping;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Form2EnagagePrimaryTableDataBean> getForm2Engage() {
		return form2Engage;
	}

	public void setForm2Engage(List<Form2EnagagePrimaryTableDataBean> form2Engage) {
		this.form2Engage = form2Engage;
	}

	public List<Form2EnagageStakeholdersTableDataBean> getForm2stakeholders() {
		return form2stakeholders;
	}

	public void setForm2stakeholders(List<Form2EnagageStakeholdersTableDataBean> form2stakeholders) {
		this.form2stakeholders = form2stakeholders;
	}

	public List<Form2EnagageSectiondetails> getForm2docs() {
		return form2docs;
	}

	public void setForm2docs(List<Form2EnagageSectiondetails> form2docs) {
		this.form2docs = form2docs;
	}

	public List<Form2EngagestakeIDDetailsBean> getMapping() {
		return mapping;
	}

	public void setMapping(List<Form2EngagestakeIDDetailsBean> mapping) {
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
		return "Form2EnagageSendAllDataBean [form2Engage=" + form2Engage + ", form2stakeholders=" + form2stakeholders
				+ ", form2docs=" + form2docs + ", mapping=" + mapping + ", error_code=" + error_code + ", message="
				+ message + "]";
	}

	
}
