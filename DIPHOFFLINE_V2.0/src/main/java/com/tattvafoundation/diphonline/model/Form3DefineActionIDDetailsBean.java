package com.tattvafoundation.diphonline.model;

public class Form3DefineActionIDDetailsBean {

	private String district;
	private String cycle;
	private String year;
	private String appsend_actionid;
	private String webgen_actionid;
	private String form4planfillcontinously;
	private String form5followfillcontinously;
	
	public Form3DefineActionIDDetailsBean() {
		
	}

	public Form3DefineActionIDDetailsBean(String district, String cycle, String year, String appsend_actionid,
			String webgen_actionid, String form4planfillcontinously, String form5followfillcontinously) {		
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.appsend_actionid = appsend_actionid;
		this.webgen_actionid = webgen_actionid;
		this.form4planfillcontinously = form4planfillcontinously;
		this.form5followfillcontinously = form5followfillcontinously;
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

	public String getAppsend_actionid() {
		return appsend_actionid;
	}

	public void setAppsend_actionid(String appsend_actionid) {
		this.appsend_actionid = appsend_actionid;
	}

	public String getWebgen_actionid() {
		return webgen_actionid;
	}

	public void setWebgen_actionid(String webgen_actionid) {
		this.webgen_actionid = webgen_actionid;
	}

	public String getForm4planfillcontinously() {
		return form4planfillcontinously;
	}

	public void setForm4planfillcontinously(String form4planfillcontinously) {
		this.form4planfillcontinously = form4planfillcontinously;
	}

	public String getForm5followfillcontinously() {
		return form5followfillcontinously;
	}

	public void setForm5followfillcontinously(String form5followfillcontinously) {
		this.form5followfillcontinously = form5followfillcontinously;
	}

	@Override
	public String toString() {
		return "Form3DefineActionIDDetailsBean [district=" + district + ", cycle=" + cycle + ", year=" + year
				+ ", appsend_actionid=" + appsend_actionid + ", webgen_actionid=" + webgen_actionid
				+ ", form4planfillcontinously=" + form4planfillcontinously + ", form5followfillcontinously="
				+ form5followfillcontinously + "]";
	}
	
	
}
