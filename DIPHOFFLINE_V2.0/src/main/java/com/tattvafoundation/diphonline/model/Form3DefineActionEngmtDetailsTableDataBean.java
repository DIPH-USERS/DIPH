package com.tattvafoundation.diphonline.model;

public class Form3DefineActionEngmtDetailsTableDataBean {

	private String auto_id;
	private String form_3_id;
	private String action_name;
	private String description_text;
	private String possible_soln;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	
	
	public Form3DefineActionEngmtDetailsTableDataBean() {
		
	}



	public Form3DefineActionEngmtDetailsTableDataBean(String auto_id, String form_3_id, String action_name,
			String description_text, String possible_soln, String country_id, String province_id, String district_id,
			String cycle_id, String year, String user_id, String recordcreated, String timestamp, String datafrom) {
		
		this.auto_id = auto_id;
		this.form_3_id = form_3_id;
		this.action_name = action_name;
		this.description_text = description_text;
		this.possible_soln = possible_soln;
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



	public String getAction_name() {
		return action_name;
	}



	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}



	public String getDescription_text() {
		return description_text;
	}



	public void setDescription_text(String description_text) {
		this.description_text = description_text;
	}



	public String getPossible_soln() {
		return possible_soln;
	}



	public void setPossible_soln(String possible_soln) {
		this.possible_soln = possible_soln;
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
		return "Form3DefineActionEngmtDetailsTableDataBean [auto_id=" + auto_id + ", form_3_id=" + form_3_id
				+ ", action_name=" + action_name + ", description_text=" + description_text + ", possible_soln="
				+ possible_soln + ", country_id=" + country_id + ", province_id=" + province_id + ", district_id="
				+ district_id + ", cycle_id=" + cycle_id + ", year=" + year + ", user_id=" + user_id
				+ ", recordcreated=" + recordcreated + ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	} 
	
	
	
}
