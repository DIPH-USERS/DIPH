package com.tattvafoundation.diphonline.model;

public class Form2EnagageStakeholdersTableDataBean {
	
	private String auto_id;
	private String form_2engage_id;
	private String stake_label;
	private String stakeholder_text;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form2EnagageStakeholdersTableDataBean() {
	}

	public Form2EnagageStakeholdersTableDataBean(String auto_id, String form_2engage_id, String stake_label,
			String stakeholder_text, String country_id, String province_id, String district_id, String cycle_id,
			String year, String user_id, String recordcreated, String timestamp, String datafrom) {
		this.auto_id = auto_id;
		this.form_2engage_id = form_2engage_id;
		this.stake_label = stake_label;
		this.stakeholder_text = stakeholder_text;
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

	public String getForm_2engage_id() {
		return form_2engage_id;
	}

	public void setForm_2engage_id(String form_2engage_id) {
		this.form_2engage_id = form_2engage_id;
	}

	public String getStake_label() {
		return stake_label;
	}

	public void setStake_label(String stake_label) {
		this.stake_label = stake_label;
	}

	public String getStakeholder_text() {
		return stakeholder_text;
	}

	public void setStakeholder_text(String stakeholder_text) {
		this.stakeholder_text = stakeholder_text;
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
		return "Form2EnagageStakeholdersTableDataBean [auto_id=" + auto_id + ", form_2engage_id=" + form_2engage_id
				+ ", stake_label=" + stake_label + ", stakeholder_text=" + stakeholder_text + ", country_id="
				+ country_id + ", province_id=" + province_id + ", district_id=" + district_id + ", cycle_id="
				+ cycle_id + ", year=" + year + ", user_id=" + user_id + ", recordcreated=" + recordcreated
				+ ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	}	
		
}
