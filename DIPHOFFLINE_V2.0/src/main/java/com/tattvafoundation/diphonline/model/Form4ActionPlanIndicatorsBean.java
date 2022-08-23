package com.tattvafoundation.diphonline.model;

public class Form4ActionPlanIndicatorsBean {

	private String auto_id;
	private String form4_id;	
	private String form3_actionreq_pkey;
	private String action_responsible_id;
	private String form3_actionreq_pkey_real_value;
	private String indicator_name; 
	private String indicator_id; 
	private String area_name; 
	private String area_id;
	private String target_value;	
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;	
	
	
	
	public Form4ActionPlanIndicatorsBean() {
		
	}



	public Form4ActionPlanIndicatorsBean(String auto_id, String form4_id, String form3_actionreq_pkey,
			String action_responsible_id, String form3_actionreq_pkey_real_value, String indicator_name,
			String indicator_id, String area_name, String area_id, String target_value, String country_id,
			String province_id, String district_id, String cycle_id, String year, String user_id, String recordcreated,
			String timestamp, String datafrom) {		
		this.auto_id = auto_id;
		this.form4_id = form4_id;
		this.form3_actionreq_pkey = form3_actionreq_pkey;
		this.action_responsible_id = action_responsible_id;
		this.form3_actionreq_pkey_real_value = form3_actionreq_pkey_real_value;
		this.indicator_name = indicator_name;
		this.indicator_id = indicator_id;
		this.area_name = area_name;
		this.area_id = area_id;
		this.target_value = target_value;
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



	public String getForm4_id() {
		return form4_id;
	}



	public void setForm4_id(String form4_id) {
		this.form4_id = form4_id;
	}



	public String getForm3_actionreq_pkey() {
		return form3_actionreq_pkey;
	}



	public void setForm3_actionreq_pkey(String form3_actionreq_pkey) {
		this.form3_actionreq_pkey = form3_actionreq_pkey;
	}



	public String getAction_responsible_id() {
		return action_responsible_id;
	}



	public void setAction_responsible_id(String action_responsible_id) {
		this.action_responsible_id = action_responsible_id;
	}



	public String getForm3_actionreq_pkey_real_value() {
		return form3_actionreq_pkey_real_value;
	}



	public void setForm3_actionreq_pkey_real_value(String form3_actionreq_pkey_real_value) {
		this.form3_actionreq_pkey_real_value = form3_actionreq_pkey_real_value;
	}



	public String getIndicator_name() {
		return indicator_name;
	}



	public void setIndicator_name(String indicator_name) {
		this.indicator_name = indicator_name;
	}



	public String getIndicator_id() {
		return indicator_id;
	}



	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}



	public String getArea_name() {
		return area_name;
	}



	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}



	public String getArea_id() {
		return area_id;
	}



	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}



	public String getTarget_value() {
		return target_value;
	}



	public void setTarget_value(String target_value) {
		this.target_value = target_value;
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
		return "Form4ActionPlanIndicatorsBean [auto_id=" + auto_id + ", form4_id=" + form4_id
				+ ", form3_actionreq_pkey=" + form3_actionreq_pkey + ", action_responsible_id=" + action_responsible_id
				+ ", form3_actionreq_pkey_real_value=" + form3_actionreq_pkey_real_value + ", indicator_name="
				+ indicator_name + ", indicator_id=" + indicator_id + ", area_name=" + area_name + ", area_id="
				+ area_id + ", target_value=" + target_value + ", country_id=" + country_id + ", province_id="
				+ province_id + ", district_id=" + district_id + ", cycle_id=" + cycle_id + ", year=" + year
				+ ", user_id=" + user_id + ", recordcreated=" + recordcreated + ", timestamp=" + timestamp
				+ ", datafrom=" + datafrom + "]";
	}

	
	
}
