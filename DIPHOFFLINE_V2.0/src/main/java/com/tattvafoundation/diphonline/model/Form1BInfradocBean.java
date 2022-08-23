package com.tattvafoundation.diphonline.model;

public class Form1BInfradocBean {

	private String auto_id;
	private String hsca_district_demographic_id;
	private String infradetails;
	private String infrasanctioned;
	private String infraavailable;
	private String infragap;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form1BInfradocBean() {
	}
	
	public Form1BInfradocBean(String auto_id, String hsca_district_demographic_id, String infradetails,
			String infrasanctioned, String infraavailable, String infragap, String country_id, String province_id,
			String district_id, String cycle_id, String year, String user_id, String recordcreated, String timestamp,
			String datafrom) {
		this.auto_id = auto_id;
		this.hsca_district_demographic_id = hsca_district_demographic_id;
		this.infradetails = infradetails;
		this.infrasanctioned = infrasanctioned;
		this.infraavailable = infraavailable;
		this.infragap = infragap;
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

	public String getInfradetails() {
		return infradetails;
	}

	public void setInfradetails(String infradetails) {
		this.infradetails = infradetails;
	}

	public String getInfrasanctioned() {
		return infrasanctioned;
	}

	public void setInfrasanctioned(String infrasanctioned) {
		this.infrasanctioned = infrasanctioned;
	}

	public String getInfraavailable() {
		return infraavailable;
	}

	public void setInfraavailable(String infraavailable) {
		this.infraavailable = infraavailable;
	}

	public String getInfragap() {
		return infragap;
	}

	public void setInfragap(String infragap) {
		this.infragap = infragap;
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
		return "Form1BInfradocBean [auto_id=" + auto_id + ", hsca_district_demographic_id="
				+ hsca_district_demographic_id + ", infradetails=" + infradetails + ", infrasanctioned="
				+ infrasanctioned + ", infraavailable=" + infraavailable + ", infragap=" + infragap + ", country_id="
				+ country_id + ", province_id=" + province_id + ", district_id=" + district_id + ", cycle_id="
				+ cycle_id + ", year=" + year + ", user_id=" + user_id + ", recordcreated=" + recordcreated
				+ ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	}
	
	

}
