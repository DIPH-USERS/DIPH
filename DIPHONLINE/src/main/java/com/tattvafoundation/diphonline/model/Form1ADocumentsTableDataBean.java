package com.tattvafoundation.diphonline.model;

public class Form1ADocumentsTableDataBean {

	private String auto_id;
	private String meetingwith_id;
	private String documentlevel;
	private String document;
	private String sourceavailibility;
	private String documentsource;
	private String country_id;
	private String province_id;
	private String distrct_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	
	public Form1ADocumentsTableDataBean() {
		
	}


	public Form1ADocumentsTableDataBean(String auto_id, String meetingwith_id, String documentlevel, String document,
			String sourceavailibility, String documentsource, String country_id, String province_id, String distrct_id,
			String cycle_id, String year, String user_id, String recordcreated, String timestamp, String datafrom) {
		this.auto_id = auto_id;
		this.meetingwith_id = meetingwith_id;
		this.documentlevel = documentlevel;
		this.document = document;
		this.sourceavailibility = sourceavailibility;
		this.documentsource = documentsource;
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


	public String getDocument() {
		return document;
	}


	public void setDocument(String document) {
		this.document = document;
	}


	public String getSourceavailibility() {
		return sourceavailibility;
	}


	public void setSourceavailibility(String sourceavailibility) {
		this.sourceavailibility = sourceavailibility;
	}


	public String getDocumentsource() {
		return documentsource;
	}


	public void setDocumentsource(String documentsource) {
		this.documentsource = documentsource;
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
		return "Form1ADocumentsTableDataBean [auto_id=" + auto_id + ", meetingwith_id=" + meetingwith_id
				+ ", documentlevel=" + documentlevel + ", document=" + document + ", sourceavailibility="
				+ sourceavailibility + ", documentsource=" + documentsource + ", country_id=" + country_id
				+ ", province_id=" + province_id + ", distrct_id=" + distrct_id + ", cycle_id=" + cycle_id + ", year="
				+ year + ", user_id=" + user_id + ", recordcreated=" + recordcreated + ", timestamp=" + timestamp
				+ ", datafrom=" + datafrom + "]";
	}
	
	  
}
