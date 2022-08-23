package com.tattvafoundation.diphonline.model;

public class Form1BStakeHolderTableDataBean {

	private String auto_id;
	private String hsca_district_demographic_id;
	private String ngoname;
	private String ngocontactdetails;	
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	
	public Form1BStakeHolderTableDataBean() {
		
	}


	public Form1BStakeHolderTableDataBean(String auto_id, String hsca_district_demographic_id, String ngoname,
			String ngocontactdetails, String country_id, String province_id, String district_id, String cycle_id,
			String year, String user_id, String recordcreated, String timestamp, String datafrom) {
		this.auto_id = auto_id;
		this.hsca_district_demographic_id = hsca_district_demographic_id;
		this.ngoname = ngoname;
		this.ngocontactdetails = ngocontactdetails;
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


	public String getHsca_district_demographic_id() {
		return hsca_district_demographic_id;
	}


	public void setHsca_district_demographic_id(String hsca_district_demographic_id) {
		this.hsca_district_demographic_id = hsca_district_demographic_id;
	}


	public String getNgoname() {
		return ngoname;
	}


	public void setNgoname(String ngoname) {
		this.ngoname = ngoname;
	}


	public String getNgocontactdetails() {
		return ngocontactdetails;
	}


	public void setNgocontactdetails(String ngocontactdetails) {
		this.ngocontactdetails = ngocontactdetails;
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
		return "Form1BStakeHolderTableDataBean [auto_id=" + auto_id + ", hsca_district_demographic_id="
				+ hsca_district_demographic_id + ", ngoname=" + ngoname + ", ngocontactdetails=" + ngocontactdetails
				+ ", country_id=" + country_id + ", province_id=" + province_id + ", district_id=" + district_id
				+ ", cycle_id=" + cycle_id + ", year=" + year + ", user_id=" + user_id + ", recordcreated="
				+ recordcreated + ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	}
	
	
}
