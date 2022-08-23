package com.tattvafoundation.diphonline.model;

public class FormSupplementaryAPrimaryTableDataBean {

	private String auto_id;
	private String document_title;
	private String date_of_release;
	private String primary_theme;
	private String goal;
	private String part;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	private String completed;
	
	public FormSupplementaryAPrimaryTableDataBean() {		
	}

	public FormSupplementaryAPrimaryTableDataBean(String auto_id, String document_title, String date_of_release,
			String primary_theme, String goal, String part, String country_id, String province_id, String district_id,
			String cycle_id, String year, String user_id, String recordcreated, String timestamp, String datafrom, String completed) {
		this.auto_id = auto_id;
		this.document_title = document_title;
		this.date_of_release = date_of_release;
		this.primary_theme = primary_theme;
		this.goal = goal;
		this.part = part;
		this.country_id = country_id;
		this.province_id = province_id;
		this.district_id = district_id;
		this.cycle_id = cycle_id;
		this.year = year;
		this.user_id = user_id;
		this.recordcreated = recordcreated;
		this.timestamp = timestamp;
		this.datafrom = datafrom;
		this.completed = completed;
	}

	public String getAuto_id() {
		return auto_id;
	}

	public void setAuto_id(String auto_id) {
		this.auto_id = auto_id;
	}

	public String getDocument_title() {
		return document_title;
	}

	public void setDocument_title(String document_title) {
		this.document_title = document_title;
	}

	public String getDate_of_release() {
		return date_of_release;
	}

	public void setDate_of_release(String date_of_release) {
		this.date_of_release = date_of_release;
	}

	public String getPrimary_theme() {
		return primary_theme;
	}

	public void setPrimary_theme(String primary_theme) {
		this.primary_theme = primary_theme;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
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

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "FormSupplementaryAPrimaryTableDataBean [auto_id=" + auto_id + ", document_title=" + document_title
				+ ", date_of_release=" + date_of_release + ", primary_theme=" + primary_theme + ", goal=" + goal
				+ ", part=" + part + ", country_id=" + country_id + ", province_id=" + province_id + ", district_id="
				+ district_id + ", cycle_id=" + cycle_id + ", year=" + year + ", user_id=" + user_id
				+ ", recordcreated=" + recordcreated + ", timestamp=" + timestamp + ", datafrom=" + datafrom
				+ ", completed=" + completed + "]";
	}

	
	
}
