package com.tattvafoundation.diphonline.model;

import java.util.List;


public class Form1AView {
	
	private String date_of_verification;
	private String filled_by;
	private String verified_by_name;
	private String verified_by_name_actual_name;
	private String doc_db_check_id;
	private String completed;

	
	private List<Form1ASaveDocumentsArray> large_scale_district_array;
	private List<Form1ASaveDocumentsArray> private_sec_array;
	private List<Form1ASaveDocumentsArray> non_health_dept_array;
	private List<Form1ASaveDocumentsArray> health_dept_array;
	private List<Form1ASaveDocumentsArray> district_policy_array;
	private List<Form1ASaveDocumentsArray> state_policy_array;
	
	
	public Form1AView() {
		
	}


	public Form1AView(String date_of_verification, String filled_by, String verified_by_name,
			String verified_by_name_actual_name, String doc_db_check_id,String completed,
			List<Form1ASaveDocumentsArray> large_scale_district_array, List<Form1ASaveDocumentsArray> private_sec_array,
			List<Form1ASaveDocumentsArray> non_health_dept_array, List<Form1ASaveDocumentsArray> health_dept_array,
			List<Form1ASaveDocumentsArray> district_policy_array, List<Form1ASaveDocumentsArray> state_policy_array) {
		this.date_of_verification = date_of_verification;
		this.filled_by = filled_by;
		this.verified_by_name = verified_by_name;
		this.verified_by_name_actual_name = verified_by_name_actual_name;
		this.doc_db_check_id = doc_db_check_id;
		this.large_scale_district_array = large_scale_district_array;
		this.private_sec_array = private_sec_array;
		this.non_health_dept_array = non_health_dept_array;
		this.health_dept_array = health_dept_array;
		this.district_policy_array = district_policy_array;
		this.state_policy_array = state_policy_array;
		this.completed = completed;
	}


	public String getDate_of_verification() {
		return date_of_verification;
	}


	public void setDate_of_verification(String date_of_verification) {
		this.date_of_verification = date_of_verification;
	}


	public String getFilled_by() {
		return filled_by;
	}


	public void setFilled_by(String filled_by) {
		this.filled_by = filled_by;
	}


	public String getVerified_by_name() {
		return verified_by_name;
	}


	public void setVerified_by_name(String verified_by_name) {
		this.verified_by_name = verified_by_name;
	}


	public String getVerified_by_name_actual_name() {
		return verified_by_name_actual_name;
	}


	public void setVerified_by_name_actual_name(String verified_by_name_actual_name) {
		this.verified_by_name_actual_name = verified_by_name_actual_name;
	}


	public String getDoc_db_check_id() {
		return doc_db_check_id;
	}


	public void setDoc_db_check_id(String doc_db_check_id) {
		this.doc_db_check_id = doc_db_check_id;
	}


	public List<Form1ASaveDocumentsArray> getLarge_scale_district_array() {
		return large_scale_district_array;
	}


	public void setLarge_scale_district_array(List<Form1ASaveDocumentsArray> large_scale_district_array) {
		this.large_scale_district_array = large_scale_district_array;
	}


	public List<Form1ASaveDocumentsArray> getPrivate_sec_array() {
		return private_sec_array;
	}


	public void setPrivate_sec_array(List<Form1ASaveDocumentsArray> private_sec_array) {
		this.private_sec_array = private_sec_array;
	}


	public List<Form1ASaveDocumentsArray> getNon_health_dept_array() {
		return non_health_dept_array;
	}


	public void setNon_health_dept_array(List<Form1ASaveDocumentsArray> non_health_dept_array) {
		this.non_health_dept_array = non_health_dept_array;
	}


	public List<Form1ASaveDocumentsArray> getHealth_dept_array() {
		return health_dept_array;
	}


	public void setHealth_dept_array(List<Form1ASaveDocumentsArray> health_dept_array) {
		this.health_dept_array = health_dept_array;
	}


	public List<Form1ASaveDocumentsArray> getDistrict_policy_array() {
		return district_policy_array;
	}


	public void setDistrict_policy_array(List<Form1ASaveDocumentsArray> district_policy_array) {
		this.district_policy_array = district_policy_array;
	}


	public List<Form1ASaveDocumentsArray> getState_policy_array() {
		return state_policy_array;
	}


	public void setState_policy_array(List<Form1ASaveDocumentsArray> state_policy_array) {
		this.state_policy_array = state_policy_array;
	}

	

	public String getCompleted() {
		return completed;
	}


	public void setCompleted(String completed) {
		this.completed = completed;
	}


	@Override
	public String toString() {
		return "Form1AView [date_of_verification=" + date_of_verification + ", filled_by=" + filled_by
				+ ", verified_by_name=" + verified_by_name + ", verified_by_name_actual_name="
				+ verified_by_name_actual_name + ", doc_db_check_id=" + doc_db_check_id + ", completed=" + completed
				+ ", large_scale_district_array=" + large_scale_district_array + ", private_sec_array="
				+ private_sec_array + ", non_health_dept_array=" + non_health_dept_array + ", health_dept_array="
				+ health_dept_array + ", district_policy_array=" + district_policy_array + ", state_policy_array="
				+ state_policy_array + "]";
	}


	

	
	
}
