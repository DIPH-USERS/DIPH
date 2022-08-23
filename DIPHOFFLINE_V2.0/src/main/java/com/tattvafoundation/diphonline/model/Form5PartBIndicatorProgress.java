package com.tattvafoundation.diphonline.model;

public class Form5PartBIndicatorProgress {

	
	private String auto_id;
	private String followup_id_server;
	private String followuppartb_id_server;
	private String action_point_id;
	private String indicator_id;
	private String progress_indicators;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form5PartBIndicatorProgress() {
	}
	
	public Form5PartBIndicatorProgress(String auto_id, String followup_id_server, String followuppartb_id_server,
			String action_point_id, String indicator_id, String progress_indicators, String country_id,
			String province_id, String district_id, String cycle_id, String year, String user_id, String recordcreated,
			String timestamp, String datafrom) {
		this.auto_id = auto_id;
		this.followup_id_server = followup_id_server;
		this.followuppartb_id_server = followuppartb_id_server;
		this.action_point_id = action_point_id;
		this.indicator_id = indicator_id;
		this.progress_indicators = progress_indicators;
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

	public String getFollowup_id_server() {
		return followup_id_server;
	}

	public void setFollowup_id_server(String followup_id_server) {
		this.followup_id_server = followup_id_server;
	}

	public String getFollowuppartb_id_server() {
		return followuppartb_id_server;
	}

	public void setFollowuppartb_id_server(String followuppartb_id_server) {
		this.followuppartb_id_server = followuppartb_id_server;
	}

	public String getAction_point_id() {
		return action_point_id;
	}

	public void setAction_point_id(String action_point_id) {
		this.action_point_id = action_point_id;
	}

	public String getIndicator_id() {
		return indicator_id;
	}

	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}

	public String getProgress_indicators() {
		return progress_indicators;
	}

	public void setProgress_indicators(String progress_indicators) {
		this.progress_indicators = progress_indicators;
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
		return "Form5PartBIndicatorProgress [auto_id=" + auto_id + ", followup_id_server=" + followup_id_server
				+ ", followuppartb_id_server=" + followuppartb_id_server + ", action_point_id=" + action_point_id
				+ ", indicator_id=" + indicator_id + ", progress_indicators=" + progress_indicators + ", country_id="
				+ country_id + ", province_id=" + province_id + ", district_id=" + district_id + ", cycle_id="
				+ cycle_id + ", year=" + year + ", user_id=" + user_id + ", recordcreated=" + recordcreated
				+ ", timestamp=" + timestamp + ", datafrom=" + datafrom + "]";
	}
	
	
}
