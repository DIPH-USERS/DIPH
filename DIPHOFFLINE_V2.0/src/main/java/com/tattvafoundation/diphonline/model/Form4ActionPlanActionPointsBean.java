package com.tattvafoundation.diphonline.model;

public class Form4ActionPlanActionPointsBean {

	private String auto_id;
	private String form4_id;
	private String action_part;
	private String form3_actionreq_pkey;
	private String form3_actionreq_pkey_real_value;
	private String responsible_dept;
	private String person_responsible;
	private String timeline;
	
	
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form4ActionPlanActionPointsBean() {
		
	}

	public Form4ActionPlanActionPointsBean(String auto_id, String form4_id, String action_part,
			String form3_actionreq_pkey, String form3_actionreq_pkey_real_value, String responsible_dept,
			String person_responsible, String timeline, String country_id, String province_id, String district_id,
			String cycle_id, String year, String user_id, String recordcreated, String timestamp, String datafrom) {		
		this.auto_id = auto_id;
		this.form4_id = form4_id;
		this.action_part = action_part;
		this.form3_actionreq_pkey = form3_actionreq_pkey;
		this.form3_actionreq_pkey_real_value = form3_actionreq_pkey_real_value;
		this.responsible_dept = responsible_dept;
		this.person_responsible = person_responsible;
		this.timeline = timeline;
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

	public String getAction_part() {
		return action_part;
	}

	public void setAction_part(String action_part) {
		this.action_part = action_part;
	}

	public String getForm3_actionreq_pkey() {
		return form3_actionreq_pkey;
	}

	public void setForm3_actionreq_pkey(String form3_actionreq_pkey) {
		this.form3_actionreq_pkey = form3_actionreq_pkey;
	}

	public String getForm3_actionreq_pkey_real_value() {
		return form3_actionreq_pkey_real_value;
	}

	public void setForm3_actionreq_pkey_real_value(String form3_actionreq_pkey_real_value) {
		this.form3_actionreq_pkey_real_value = form3_actionreq_pkey_real_value;
	}

	public String getResponsible_dept() {
		return responsible_dept;
	}

	public void setResponsible_dept(String responsible_dept) {
		this.responsible_dept = responsible_dept;
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
		return "Form4ActionPlanActionPointsBean [auto_id=" + auto_id + ", form4_id=" + form4_id + ", action_part="
				+ action_part + ", form3_actionreq_pkey=" + form3_actionreq_pkey + ", form3_actionreq_pkey_real_value="
				+ form3_actionreq_pkey_real_value + ", responsible_dept=" + responsible_dept + ", person_responsible="
				+ person_responsible + ", timeline=" + timeline + ", country_id=" + country_id + ", province_id="
				+ province_id + ", district_id=" + district_id + ", cycle_id=" + cycle_id + ", year=" + year
				+ ", user_id=" + user_id + ", recordcreated=" + recordcreated + ", timestamp=" + timestamp
				+ ", datafrom=" + datafrom + "]";
	}

	
}
