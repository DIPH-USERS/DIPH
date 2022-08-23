package com.tattvafoundation.diphonline.model;

public class Form1BIndicatorsDetailsBean {

	private String district;
	private String cycle;
	private String year;
	private String appsend_indi_id;
	private String webgen_indi_id;
	private String form5fillcontinously; 
	
	
	public Form1BIndicatorsDetailsBean() {
		
	}


	public Form1BIndicatorsDetailsBean(String district, String cycle, String year, String appsend_indi_id,
			String webgen_indi_id, String form5fillcontinously) {		
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.appsend_indi_id = appsend_indi_id;
		this.webgen_indi_id = webgen_indi_id;
		this.form5fillcontinously = form5fillcontinously;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getCycle() {
		return cycle;
	}


	public void setCycle(String cycle) {
		this.cycle = cycle;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getAppsend_indi_id() {
		return appsend_indi_id;
	}


	public void setAppsend_indi_id(String appsend_indi_id) {
		this.appsend_indi_id = appsend_indi_id;
	}


	public String getWebgen_indi_id() {
		return webgen_indi_id;
	}


	public void setWebgen_indi_id(String webgen_indi_id) {
		this.webgen_indi_id = webgen_indi_id;
	}


	public String getForm5fillcontinously() {
		return form5fillcontinously;
	}


	public void setForm5fillcontinously(String form5fillcontinously) {
		this.form5fillcontinously = form5fillcontinously;
	}


	@Override
	public String toString() {
		return "Form1BIndicatorsDetailsBean [district=" + district + ", cycle=" + cycle + ", year=" + year
				+ ", appsend_indi_id=" + appsend_indi_id + ", webgen_indi_id=" + webgen_indi_id
				+ ", form5fillcontinously=" + form5fillcontinously + "]";
	}
	
	
	
}
