package com.tattvafoundation.diphonline.model;

public class Form1BDocumentsTableDataBean {
	
	private String auto_id;
	private String meetingwith_id;
	private String documentlevel;
	private String document_details;
	private String document_sanctioned;
	private String document_available_functional;
	private String document_gap;
	
	private String country_id;
	private String province_id;
	private String distrct_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form1BDocumentsTableDataBean() {
		
	}
	
	public Form1BDocumentsTableDataBean(String auto_id, String meetingwith_id, String documentlevel,
			String document_details, String document_sanctioned, String document_available_functional,
			String document_gap, String country_id, String province_id, String distrct_id, String cycle_id, String year,
			String user_id, String recordcreated, String timestamp, String datafrom) {
		this.auto_id = auto_id;
		this.meetingwith_id = meetingwith_id;
		this.documentlevel = documentlevel;
		this.document_details = document_details;
		this.document_sanctioned = document_sanctioned;
		this.document_available_functional = document_available_functional;
		this.document_gap = document_gap;
		this.country_id = country_id;
		this.province_id = province_id;
		this.distrct_id = distrct_id;
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

	public String getMeetingwith_id() {
		return meetingwith_id;
	}

	public void setMeetingwith_id(String meetingwith_id) {
		this.meetingwith_id = meetingwith_id;
	}

	public String getDocumentlevel() {
		return documentlevel;
	}

	public void setDocumentlevel(String documentlevel) {
		this.documentlevel = documentlevel;
	}

	public String getDocument_details() {
		return document_details;
	}

	public void setDocument_details(String document_details) {
		this.document_details = document_details;
	}

	public String getDocument_sanctioned() {
		return document_sanctioned;
	}

	public void setDocument_sanctioned(String document_sanctioned) {
		this.document_sanctioned = document_sanctioned;
	}

	public String getDocument_available_functional() {
		return document_available_functional;
	}

	public void setDocument_available_functional(String document_available_functional) {
		this.document_available_functional = document_available_functional;
	}

	public String getDocument_gap() {
		return document_gap;
	}

	public void setDocument_gap(String document_gap) {
		this.document_gap = document_gap;
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

	public String getDistrct_id() {
		return distrct_id;
	}

	public void setDistrct_id(String distrct_id) {
		this.distrct_id = distrct_id;
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
		return "Form1BDocumentsTableDataBean [auto_id=" + auto_id + ", meetingwith_id=" + meetingwith_id
				+ ", documentlevel=" + documentlevel + ", document_details=" + document_details
				+ ", document_sanctioned=" + document_sanctioned + ", document_available_functional="
				+ document_available_functional + ", document_gap=" + document_gap + ", country_id=" + country_id
				+ ", province_id=" + province_id + ", distrct_id=" + distrct_id + ", cycle_id=" + cycle_id + ", year="
				+ year + ", user_id=" + user_id + ", recordcreated=" + recordcreated + ", timestamp=" + timestamp
				+ ", datafrom=" + datafrom + "]";
	}
	
	
	

}
