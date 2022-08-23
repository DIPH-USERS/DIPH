package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form3DefineSendAllDataBean {

	private List<Form3DefinePrimaryTableDataBean> form3Define;
	private List<Form3DefineActionEngmtDetailsTableDataBean> form3ActionEngmtDetails;
	private List<Form3ActionEngmtActionReqTableDataBean> form3ActionEngmtActionReq;
	private List<Form3DefineActionIDDetailsBean> mapping;
	private String error_code;
	private String message;
	
	
	
	public Form3DefineSendAllDataBean() {
		
	}



	public Form3DefineSendAllDataBean(List<Form3DefinePrimaryTableDataBean> form3Define,
			List<Form3DefineActionEngmtDetailsTableDataBean> form3ActionEngmtDetails,
			List<Form3ActionEngmtActionReqTableDataBean> form3ActionEngmtActionReq,
			List<Form3DefineActionIDDetailsBean> mapping, String error_code, String message) {
		
		this.form3Define = form3Define;
		this.form3ActionEngmtDetails = form3ActionEngmtDetails;
		this.form3ActionEngmtActionReq = form3ActionEngmtActionReq;
		this.mapping = mapping;
		this.error_code = error_code;
		this.message = message;
	}



	public List<Form3DefinePrimaryTableDataBean> getForm3Define() {
		return form3Define;
	}



	public void setForm3Define(List<Form3DefinePrimaryTableDataBean> form3Define) {
		this.form3Define = form3Define;
	}



	public List<Form3DefineActionEngmtDetailsTableDataBean> getForm3ActionEngmtDetails() {
		return form3ActionEngmtDetails;
	}



	public void setForm3ActionEngmtDetails(List<Form3DefineActionEngmtDetailsTableDataBean> form3ActionEngmtDetails) {
		this.form3ActionEngmtDetails = form3ActionEngmtDetails;
	}



	public List<Form3ActionEngmtActionReqTableDataBean> getForm3ActionEngmtActionReq() {
		return form3ActionEngmtActionReq;
	}



	public void setForm3ActionEngmtActionReq(List<Form3ActionEngmtActionReqTableDataBean> form3ActionEngmtActionReq) {
		this.form3ActionEngmtActionReq = form3ActionEngmtActionReq;
	}



	public List<Form3DefineActionIDDetailsBean> getMapping() {
		return mapping;
	}



	public void setMapping(List<Form3DefineActionIDDetailsBean> mapping) {
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
		return "Form3DefineSendAllDataBean [form3Define=" + form3Define + ", form3ActionEngmtDetails="
				+ form3ActionEngmtDetails + ", form3ActionEngmtActionReq=" + form3ActionEngmtActionReq + ", mapping="
				+ mapping + ", error_code=" + error_code + ", message=" + message + "]";
	}
	
	
	
}
