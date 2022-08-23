package com.tattvafoundation.diphonline.model;

public class BasicFormInfo {

	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String source;
	
	public BasicFormInfo() {
		
	}
	
	public BasicFormInfo(String district, String cycle, String year, String userid, String source) {
		super();
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.source = source;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "BasicFormInfo [district=" + district + ", cycle=" + cycle + ", year=" + year + ", userid=" + userid
				+ ", source=" + source + "]";
	}
	
		
}
