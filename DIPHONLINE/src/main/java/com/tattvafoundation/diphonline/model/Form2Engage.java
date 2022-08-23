package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form2Engage {
	
	private String form_2_date_of_meeting;
	private String form_2_venue;
	private String form_2_filled;
	private String form_2_filled_others;
	private String primary_stake_label;
	private String primary_stakeholder_text;
	private String primary_stakeholder_id;
	private String secondary_stake_label;
	private String secondary_stakeholder_text;
	private String secondary_stakeholder_id;
	private String defining_primary_role_section_select;
	private String defining_primary_role_section_text;
	private String current_effort_to_address_the_issue_section_select;
	private String current_effort_to_address_the_issue_section_text;
	private String how_to_enhance_engagement_and_efficiency_section_select;
	private String how_to_enhance_engagement_and_efficiency_section_text;
	private String theme_lead_by_department_section_select;
	private String theme_lead_by_department_section_text;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String completed;
	private String form_2_id;
	
	private List<Form2EngagePartACommonStakeHoldersArray> primary_stake_array;
	private List<Form2EngagePartACommonStakeHoldersArray> secondary_stake_array;
	
	private List<Form2EngagePartBCommonArray> definingprimaryrole_array;
	private List<Form2EngagePartBCommonArray> efforttoaddressissue_array;
	private List<Form2EngagePartBCommonArray> enhanceefficiency_array;
	private List<Form2EngagePartBCommonArray> themeleadbydpt_array;
	private String source;
		
	
	public Form2Engage() {
		
	}


	public Form2Engage(String form_2_date_of_meeting, String form_2_venue, String form_2_filled,
			String form_2_filled_others, String primary_stake_label, String primary_stakeholder_text,
			String primary_stakeholder_id, String secondary_stake_label, String secondary_stakeholder_text,
			String secondary_stakeholder_id, String defining_primary_role_section_select,
			String defining_primary_role_section_text, String current_effort_to_address_the_issue_section_select,
			String current_effort_to_address_the_issue_section_text,
			String how_to_enhance_engagement_and_efficiency_section_select,
			String how_to_enhance_engagement_and_efficiency_section_text,
			String theme_lead_by_department_section_select, String theme_lead_by_department_section_text,
			String district, String cycle, String year, String userid, String completed, String form_2_id,
			List<Form2EngagePartACommonStakeHoldersArray> primary_stake_array,
			List<Form2EngagePartACommonStakeHoldersArray> secondary_stake_array,
			List<Form2EngagePartBCommonArray> definingprimaryrole_array,
			List<Form2EngagePartBCommonArray> efforttoaddressissue_array,
			List<Form2EngagePartBCommonArray> enhanceefficiency_array,
			List<Form2EngagePartBCommonArray> themeleadbydpt_array, String source) {
		this.form_2_date_of_meeting = form_2_date_of_meeting;
		this.form_2_venue = form_2_venue;
		this.form_2_filled = form_2_filled;
		this.form_2_filled_others = form_2_filled_others;
		this.primary_stake_label = primary_stake_label;
		this.primary_stakeholder_text = primary_stakeholder_text;
		this.primary_stakeholder_id = primary_stakeholder_id;
		this.secondary_stake_label = secondary_stake_label;
		this.secondary_stakeholder_text = secondary_stakeholder_text;
		this.secondary_stakeholder_id = secondary_stakeholder_id;
		this.defining_primary_role_section_select = defining_primary_role_section_select;
		this.defining_primary_role_section_text = defining_primary_role_section_text;
		this.current_effort_to_address_the_issue_section_select = current_effort_to_address_the_issue_section_select;
		this.current_effort_to_address_the_issue_section_text = current_effort_to_address_the_issue_section_text;
		this.how_to_enhance_engagement_and_efficiency_section_select = how_to_enhance_engagement_and_efficiency_section_select;
		this.how_to_enhance_engagement_and_efficiency_section_text = how_to_enhance_engagement_and_efficiency_section_text;
		this.theme_lead_by_department_section_select = theme_lead_by_department_section_select;
		this.theme_lead_by_department_section_text = theme_lead_by_department_section_text;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.completed = completed;
		this.form_2_id = form_2_id;
		this.primary_stake_array = primary_stake_array;
		this.secondary_stake_array = secondary_stake_array;
		this.definingprimaryrole_array = definingprimaryrole_array;
		this.efforttoaddressissue_array = efforttoaddressissue_array;
		this.enhanceefficiency_array = enhanceefficiency_array;
		this.themeleadbydpt_array = themeleadbydpt_array;
		this.source = source;
	}


	public String getForm_2_date_of_meeting() {
		return form_2_date_of_meeting;
	}


	public void setForm_2_date_of_meeting(String form_2_date_of_meeting) {
		this.form_2_date_of_meeting = form_2_date_of_meeting;
	}


	public String getForm_2_venue() {
		return form_2_venue;
	}


	public void setForm_2_venue(String form_2_venue) {
		this.form_2_venue = form_2_venue;
	}


	public String getForm_2_filled() {
		return form_2_filled;
	}


	public void setForm_2_filled(String form_2_filled) {
		this.form_2_filled = form_2_filled;
	}


	public String getForm_2_filled_others() {
		return form_2_filled_others;
	}


	public void setForm_2_filled_others(String form_2_filled_others) {
		this.form_2_filled_others = form_2_filled_others;
	}


	public String getPrimary_stake_label() {
		return primary_stake_label;
	}


	public void setPrimary_stake_label(String primary_stake_label) {
		this.primary_stake_label = primary_stake_label;
	}


	public String getPrimary_stakeholder_text() {
		return primary_stakeholder_text;
	}


	public void setPrimary_stakeholder_text(String primary_stakeholder_text) {
		this.primary_stakeholder_text = primary_stakeholder_text;
	}


	public String getPrimary_stakeholder_id() {
		return primary_stakeholder_id;
	}


	public void setPrimary_stakeholder_id(String primary_stakeholder_id) {
		this.primary_stakeholder_id = primary_stakeholder_id;
	}


	public String getSecondary_stake_label() {
		return secondary_stake_label;
	}


	public void setSecondary_stake_label(String secondary_stake_label) {
		this.secondary_stake_label = secondary_stake_label;
	}


	public String getSecondary_stakeholder_text() {
		return secondary_stakeholder_text;
	}


	public void setSecondary_stakeholder_text(String secondary_stakeholder_text) {
		this.secondary_stakeholder_text = secondary_stakeholder_text;
	}


	public String getSecondary_stakeholder_id() {
		return secondary_stakeholder_id;
	}


	public void setSecondary_stakeholder_id(String secondary_stakeholder_id) {
		this.secondary_stakeholder_id = secondary_stakeholder_id;
	}


	public String getDefining_primary_role_section_select() {
		return defining_primary_role_section_select;
	}


	public void setDefining_primary_role_section_select(String defining_primary_role_section_select) {
		this.defining_primary_role_section_select = defining_primary_role_section_select;
	}


	public String getDefining_primary_role_section_text() {
		return defining_primary_role_section_text;
	}


	public void setDefining_primary_role_section_text(String defining_primary_role_section_text) {
		this.defining_primary_role_section_text = defining_primary_role_section_text;
	}


	public String getCurrent_effort_to_address_the_issue_section_select() {
		return current_effort_to_address_the_issue_section_select;
	}


	public void setCurrent_effort_to_address_the_issue_section_select(
			String current_effort_to_address_the_issue_section_select) {
		this.current_effort_to_address_the_issue_section_select = current_effort_to_address_the_issue_section_select;
	}


	public String getCurrent_effort_to_address_the_issue_section_text() {
		return current_effort_to_address_the_issue_section_text;
	}


	public void setCurrent_effort_to_address_the_issue_section_text(
			String current_effort_to_address_the_issue_section_text) {
		this.current_effort_to_address_the_issue_section_text = current_effort_to_address_the_issue_section_text;
	}


	public String getHow_to_enhance_engagement_and_efficiency_section_select() {
		return how_to_enhance_engagement_and_efficiency_section_select;
	}


	public void setHow_to_enhance_engagement_and_efficiency_section_select(
			String how_to_enhance_engagement_and_efficiency_section_select) {
		this.how_to_enhance_engagement_and_efficiency_section_select = how_to_enhance_engagement_and_efficiency_section_select;
	}


	public String getHow_to_enhance_engagement_and_efficiency_section_text() {
		return how_to_enhance_engagement_and_efficiency_section_text;
	}


	public void setHow_to_enhance_engagement_and_efficiency_section_text(
			String how_to_enhance_engagement_and_efficiency_section_text) {
		this.how_to_enhance_engagement_and_efficiency_section_text = how_to_enhance_engagement_and_efficiency_section_text;
	}


	public String getTheme_lead_by_department_section_select() {
		return theme_lead_by_department_section_select;
	}


	public void setTheme_lead_by_department_section_select(String theme_lead_by_department_section_select) {
		this.theme_lead_by_department_section_select = theme_lead_by_department_section_select;
	}


	public String getTheme_lead_by_department_section_text() {
		return theme_lead_by_department_section_text;
	}


	public void setTheme_lead_by_department_section_text(String theme_lead_by_department_section_text) {
		this.theme_lead_by_department_section_text = theme_lead_by_department_section_text;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getCycle() {
		return cycle;
	}


	public void setCycle(String cycle) {
		this.cycle = cycle;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}
	

	public String getCompleted() {
		return completed;
	}


	public void setCompleted(String completed) {
		this.completed = completed;
	}


	public String getForm_2_id() {
		return form_2_id;
	}


	public void setForm_2_id(String form_2_id) {
		this.form_2_id = form_2_id;
	}


	public List<Form2EngagePartACommonStakeHoldersArray> getPrimary_stake_array() {
		return primary_stake_array;
	}


	public void setPrimary_stake_array(List<Form2EngagePartACommonStakeHoldersArray> primary_stake_array) {
		this.primary_stake_array = primary_stake_array;
	}


	public List<Form2EngagePartACommonStakeHoldersArray> getSecondary_stake_array() {
		return secondary_stake_array;
	}


	public void setSecondary_stake_array(List<Form2EngagePartACommonStakeHoldersArray> secondary_stake_array) {
		this.secondary_stake_array = secondary_stake_array;
	}


	public List<Form2EngagePartBCommonArray> getDefiningprimaryrole_array() {
		return definingprimaryrole_array;
	}


	public void setDefiningprimaryrole_array(List<Form2EngagePartBCommonArray> definingprimaryrole_array) {
		this.definingprimaryrole_array = definingprimaryrole_array;
	}


	public List<Form2EngagePartBCommonArray> getEfforttoaddressissue_array() {
		return efforttoaddressissue_array;
	}


	public void setEfforttoaddressissue_array(List<Form2EngagePartBCommonArray> efforttoaddressissue_array) {
		this.efforttoaddressissue_array = efforttoaddressissue_array;
	}


	public List<Form2EngagePartBCommonArray> getEnhanceefficiency_array() {
		return enhanceefficiency_array;
	}


	public void setEnhanceefficiency_array(List<Form2EngagePartBCommonArray> enhanceefficiency_array) {
		this.enhanceefficiency_array = enhanceefficiency_array;
	}


	public List<Form2EngagePartBCommonArray> getThemeleadbydpt_array() {
		return themeleadbydpt_array;
	}


	public void setThemeleadbydpt_array(List<Form2EngagePartBCommonArray> themeleadbydpt_array) {
		this.themeleadbydpt_array = themeleadbydpt_array;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	@Override
	public String toString() {
		return "Form2Engage [form_2_date_of_meeting=" + form_2_date_of_meeting + ", form_2_venue=" + form_2_venue
				+ ", form_2_filled=" + form_2_filled + ", form_2_filled_others=" + form_2_filled_others
				+ ", primary_stake_label=" + primary_stake_label + ", primary_stakeholder_text="
				+ primary_stakeholder_text + ", primary_stakeholder_id=" + primary_stakeholder_id
				+ ", secondary_stake_label=" + secondary_stake_label + ", secondary_stakeholder_text="
				+ secondary_stakeholder_text + ", secondary_stakeholder_id=" + secondary_stakeholder_id
				+ ", defining_primary_role_section_select=" + defining_primary_role_section_select
				+ ", defining_primary_role_section_text=" + defining_primary_role_section_text
				+ ", current_effort_to_address_the_issue_section_select="
				+ current_effort_to_address_the_issue_section_select
				+ ", current_effort_to_address_the_issue_section_text="
				+ current_effort_to_address_the_issue_section_text
				+ ", how_to_enhance_engagement_and_efficiency_section_select="
				+ how_to_enhance_engagement_and_efficiency_section_select
				+ ", how_to_enhance_engagement_and_efficiency_section_text="
				+ how_to_enhance_engagement_and_efficiency_section_text + ", theme_lead_by_department_section_select="
				+ theme_lead_by_department_section_select + ", theme_lead_by_department_section_text="
				+ theme_lead_by_department_section_text + ", district=" + district + ", cycle=" + cycle + ", year="
				+ year + ", userid=" + userid + ", completed=" + completed + ", form_2_id=" + form_2_id
				+ ", primary_stake_array=" + primary_stake_array + ", secondary_stake_array=" + secondary_stake_array
				+ ", definingprimaryrole_array=" + definingprimaryrole_array + ", efforttoaddressissue_array="
				+ efforttoaddressissue_array + ", enhanceefficiency_array=" + enhanceefficiency_array
				+ ", themeleadbydpt_array=" + themeleadbydpt_array + ", source=" + source + "]";
	}
	
	

}