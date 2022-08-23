package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form4PlanCommonObject {

	private String action_req_id;
	private String document_action_required;
	private String form_3_1_action_part_engagement_nm_details_pkey;
	private String sel_stakeholder;
	private String person_responsible;
	private String timeline;
	private String target="0";
	private String dev_action_point_id;
	private List<Form4PlanSelIndicatorsList> sel_indicators;
	
	public Form4PlanCommonObject() {
		
	}

	public Form4PlanCommonObject(String action_req_id, String document_action_required,
			String form_3_1_action_part_engagement_nm_details_pkey, String sel_stakeholder, String person_responsible,
			String timeline, String target, String dev_action_point_id,
			List<Form4PlanSelIndicatorsList> sel_indicators) {
		this.action_req_id = action_req_id;
		this.document_action_required = document_action_required;
		this.form_3_1_action_part_engagement_nm_details_pkey = form_3_1_action_part_engagement_nm_details_pkey;
		this.sel_stakeholder = sel_stakeholder;
		this.person_responsible = person_responsible;
		this.timeline = timeline;
		this.target = target;
		this.dev_action_point_id = dev_action_point_id;
		this.sel_indicators = sel_indicators;
	}

	public String getAction_req_id() {
		return action_req_id;
	}

	public void setAction_req_id(String action_req_id) {
		this.action_req_id = action_req_id;
	}

	public String getDocument_action_required() {
		return document_action_required;
	}

	public void setDocument_action_required(String document_action_required) {
		this.document_action_required = document_action_required;
	}

	public String getForm_3_1_action_part_engagement_nm_details_pkey() {
		return form_3_1_action_part_engagement_nm_details_pkey;
	}

	public void setForm_3_1_action_part_engagement_nm_details_pkey(String form_3_1_action_part_engagement_nm_details_pkey) {
		this.form_3_1_action_part_engagement_nm_details_pkey = form_3_1_action_part_engagement_nm_details_pkey;
	}

	public String getSel_stakeholder() {
		return sel_stakeholder;
	}

	public void setSel_stakeholder(String sel_stakeholder) {
		this.sel_stakeholder = sel_stakeholder;
	}

	public String getPerson_responsible() {
		return person_responsible;
	}

	public void setPerson_responsible(String person_responsible) {
		this.person_responsible = person_responsible;
	}

	public String getTimeline() {
		return timeline;
	}

	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDev_action_point_id() {
		return dev_action_point_id;
	}

	public void setDev_action_point_id(String dev_action_point_id) {
		this.dev_action_point_id = dev_action_point_id;
	}

	public List<Form4PlanSelIndicatorsList> getSel_indicators() {
		return sel_indicators;
	}

	public void setSel_indicators(List<Form4PlanSelIndicatorsList> sel_indicators) {
		this.sel_indicators = sel_indicators;
	}

	@Override
	public String toString() {
		return "Form4PlanCommonObject [action_req_id=" + action_req_id + ", document_action_required="
				+ document_action_required + ", form_3_1_action_part_engagement_nm_details_pkey="
				+ form_3_1_action_part_engagement_nm_details_pkey + ", sel_stakeholder=" + sel_stakeholder
				+ ", person_responsible=" + person_responsible + ", timeline=" + timeline + ", target=" + target
				+ ", dev_action_point_id=" + dev_action_point_id + ", sel_indicators=" + sel_indicators + "]";
	}

	
}
