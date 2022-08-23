package com.tattvafoundation.diphonline.model;

public class Form2EngagestakeIDDetailsBean {

	private String district;
	private String cycle;
	private String year;
	private String appsend_stakesid;
	private String webgen_stakesid;
	private String form4planfillcontinously;
	
	public Form2EngagestakeIDDetailsBean() {
		
	}

	public Form2EngagestakeIDDetailsBean(String district, String cycle, String year, String appsend_stakesid,
			String webgen_stakesid, String form4planfillcontinously) {
		
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.appsend_stakesid = appsend_stakesid;
		this.webgen_stakesid = webgen_stakesid;
		this.form4planfillcontinously = form4planfillcontinously;
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

	public String getAppsend_stakesid() {
		return appsend_stakesid;
	}

	public void setAppsend_stakesid(String appsend_stakesid) {
		this.appsend_stakesid = appsend_stakesid;
	}

	public String getWebgen_stakesid() {
		return webgen_stakesid;
	}

	public void setWebgen_stakesid(String webgen_stakesid) {
		this.webgen_stakesid = webgen_stakesid;
	}

	public String getForm4planfillcontinously() {
		return form4planfillcontinously;
	}

	public void setForm4planfillcontinously(String form4planfillcontinously) {
		this.form4planfillcontinously = form4planfillcontinously;
	}

	@Override
	public String toString() {
		return "Form2EngagestakeIDDetailsBean [district=" + district + ", cycle=" + cycle + ", year=" + year
				+ ", appsend_stakesid=" + appsend_stakesid + ", webgen_stakesid=" + webgen_stakesid
				+ ", form4planfillcontinously=" + form4planfillcontinously + "]";
	}
	
	
}
