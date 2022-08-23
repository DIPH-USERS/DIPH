package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form3DefineEditOnlyArray {

	private String document_description_p_f_h_s_p;
	private String document_possible_s_t_i;
	private List<String> document_action_required;
	private List<String> action_req_id;
	private List<String> use_in_ui;
	private String form_3_1_action_part_engagement_nm_details_pkey;
	
	public Form3DefineEditOnlyArray() {
		
	}
	
	public Form3DefineEditOnlyArray(String document_description_p_f_h_s_p, String document_possible_s_t_i,
			List<String> document_action_required) {
		this.document_description_p_f_h_s_p = document_description_p_f_h_s_p;
		this.document_possible_s_t_i = document_possible_s_t_i;
		this.document_action_required = document_action_required;
		this.action_req_id = null;
		this.use_in_ui = null;
		this.form_3_1_action_part_engagement_nm_details_pkey = null;
	}
	

	public Form3DefineEditOnlyArray(String document_description_p_f_h_s_p, String document_possible_s_t_i,
			List<String> document_action_required, List<String> action_req_id, List<String> use_in_ui,
			String form_3_1_action_part_engagement_nm_details_pkey) {
		this.document_description_p_f_h_s_p = document_description_p_f_h_s_p;
		this.document_possible_s_t_i = document_possible_s_t_i;
		this.document_action_required = document_action_required;
		this.action_req_id = action_req_id;
		this.use_in_ui = use_in_ui;
		this.form_3_1_action_part_engagement_nm_details_pkey = form_3_1_action_part_engagement_nm_details_pkey;
	}

	public String getDocument_description_p_f_h_s_p() {
		return document_description_p_f_h_s_p;
	}

	public void setDocument_description_p_f_h_s_p(String document_description_p_f_h_s_p) {
		this.document_description_p_f_h_s_p = document_description_p_f_h_s_p;
	}

	public String getDocument_possible_s_t_i() {
		return document_possible_s_t_i;
	}

	public void setDocument_possible_s_t_i(String document_possible_s_t_i) {
		this.document_possible_s_t_i = document_possible_s_t_i;
	}

	public List<String> getDocument_action_required() {
		return document_action_required;
	}

	public void setDocument_action_required(List<String> document_action_required) {
		this.document_action_required = document_action_required;
	}

	public List<String> getAction_req_id() {
		return action_req_id;
	}

	public void setAction_req_id(List<String> action_req_id) {
		this.action_req_id = action_req_id;
	}

	public List<String> getUse_in_ui() {
		return use_in_ui;
	}

	public void setUse_in_ui(List<String> use_in_ui) {
		this.use_in_ui = use_in_ui;
	}

	public String getForm_3_1_action_part_engagement_nm_details_pkey() {
		return form_3_1_action_part_engagement_nm_details_pkey;
	}

	public void setForm_3_1_action_part_engagement_nm_details_pkey(String form_3_1_action_part_engagement_nm_details_pkey) {
		this.form_3_1_action_part_engagement_nm_details_pkey = form_3_1_action_part_engagement_nm_details_pkey;
	}

	@Override
	public String toString() {
		return "Form3DefineEditOnlyArray [document_description_p_f_h_s_p=" + document_description_p_f_h_s_p
				+ ", document_possible_s_t_i=" + document_possible_s_t_i + ", document_action_required="
				+ document_action_required + ", action_req_id=" + action_req_id + ", use_in_ui=" + use_in_ui
				+ ", form_3_1_action_part_engagement_nm_details_pkey=" + form_3_1_action_part_engagement_nm_details_pkey
				+ "]";
	}
	
	
	
}
