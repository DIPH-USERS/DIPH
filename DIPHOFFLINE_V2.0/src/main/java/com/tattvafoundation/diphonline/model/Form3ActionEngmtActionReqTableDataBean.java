package com.tattvafoundation.diphonline.model;

public class Form3ActionEngmtActionReqTableDataBean {

	private String auto_id;
	private String form_3_id;	
	private String action_part;
	private String prob_desc;
	private String action_required;	
	private String form_3_action_engmt_details_pkey;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	
	
	
	public Form3ActionEngmtActionReqTableDataBean() {
		
	}




	public Form3ActionEngmtActionReqTableDataBean(String auto_id, String form_3_id, String action_part,
			String prob_desc, String action_required, String form_3_action_engmt_details_pkey, String country_id,
			String province_id, String district_id, String cycle_id, String year, String user_id, String recordcreated,
			String timestamp, String datafrom) {
		
		this.auto_id = auto_id;
		this.form_3_id = form_3_id;
		this.action_part = action_part;
		this.prob_desc = prob_desc;
		this.action_required = action_required;
		this.form_3_action_engmt_details_pkey = form_3_action_engmt_details_pkey;
		this.country_id = country_id;
		this.province_id = province_id;
		this.district_id = district_id;
		this.cycle_id = cycle_id;
		this.year = year;
		this.user_id = user_id;
		this.recordcreated = recordcreated;
		this.timestamp = timestamp;
		this.datafrom = datafrom;
	}




	public String getAuto_id() {
		return auto_id;
	}




	public void setAuto_id(String auto_id) {
		this.auto_id = auto_id;
	}




	public String getForm_3_id() {
		return form_3_id;
	}




	public void setForm_3_id(String form_3_id) {
		this.form_3_id = form_3_id;
	}




	public String getAction_part() {
		return action_part;
	}




	public void setAction_part(String action_part) {
		this.action_part = action_part;
	}




	public String getProb_desc() {
		return prob_desc;
	}




	public void setProb_desc(String prob_desc) {
		this.prob_desc = prob_desc;
	}




	public String getAction_required() {
		return action_required;
	}




	public void setAction_required(String action_required) {
		this.action_required = action_required;
	}




	public String getForm_3_action_engmt_details_pkey() {
		return form_3_action_engmt_details_pkey;
	}




	public void setForm_3_action_engmt_details_pkey(String form_3_action_engmt_details_pkey) {
		this.form_3_action_engmt_details_pkey = form_3_action_engmt_details_pkey;
	}




	public String getCountry_id() {
		return country_id;
	}




	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}




	public String getProvince_id() {
		return province_id;
	}




	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}




	public String getDistrict_id() {
		return district_id;
	}




	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}




	public String getCycle_id() {
		return cycle_id;
	}




	public void setCycle_id(String cycle_id) {
		this.cycle_id = cycle_id;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getUser_id() {
		return user_id;
	}




	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}




	public String getRecordcreated() {
		return recordcreated;
	}




	public void setRecordcreated(String recordcreated) {
		this.recordcreated = recordcreated;
	}




	public String getTimestamp() {
		return timestamp;
	}




	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}




	public String getDatafrom() {
		return datafrom;
	}




	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}




	@Override
	public String toString() {
		return "Form3ActionEngmtActionReqTableDataBean [auto_id=" + auto_id + ", form_3_id=" + form_3_id
				+ ", action_part=" + action_part + ", prob_desc=" + prob_desc + ", action_required=" + action_required
				+ ", form_3_action_engmt_details_pkey=" + form_3_action_engmt_details_pkey + ", country_id="
				+ country_id + ", province_id=" + province_id + ", district_id=" + district_id + ", cycle_id="
				+ cycle_id + ", year=" + year + ", user_id=" + user_id + ", recordcreated=" + recordcreated
				+ ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	}


	
}
