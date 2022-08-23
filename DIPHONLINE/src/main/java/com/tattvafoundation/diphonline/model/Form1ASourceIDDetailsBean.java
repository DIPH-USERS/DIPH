package com.tattvafoundation.diphonline.model;

public class Form1ASourceIDDetailsBean {

	private String district;
	private String cycle;
	private String year;
	private String appsend_sourceid;
	private String webgen_sourceid;
	private String form1bfillcontinously;

	public Form1ASourceIDDetailsBean() {
	}

	public Form1ASourceIDDetailsBean(String district, String cycle, String year, String appsend_sourceid,
			String webgen_sourceid, String form1bfillcontinously) {
		
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.appsend_sourceid = appsend_sourceid;
		this.webgen_sourceid = webgen_sourceid;
		this.form1bfillcontinously = form1bfillcontinously;
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

	public String getAppsend_sourceid() {
		return appsend_sourceid;
	}

	public void setAppsend_sourceid(String appsend_sourceid) {
		this.appsend_sourceid = appsend_sourceid;
	}

	public String getWebgen_sourceid() {
		return webgen_sourceid;
	}

	public void setWebgen_sourceid(String webgen_sourceid) {
		this.webgen_sourceid = webgen_sourceid;
	}

	public String getForm1bfillcontinously() {
		return form1bfillcontinously;
	}

	public void setForm1bfillcontinously(String form1bfillcontinously) {
		this.form1bfillcontinously = form1bfillcontinously;
	}

	@Override
	public String toString() {
		return "Form1ASourceIDDetailsBean [district=" + district + ", cycle=" + cycle + ", year=" + year
				+ ", appsend_sourceid=" + appsend_sourceid + ", webgen_sourceid=" + webgen_sourceid
				+ ", form1bfillcontinously=" + form1bfillcontinously + "]";
	}

	
}
