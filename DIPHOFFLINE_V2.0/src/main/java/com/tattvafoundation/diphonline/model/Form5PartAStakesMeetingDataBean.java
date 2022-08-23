package com.tattvafoundation.diphonline.model;

public class Form5PartAStakesMeetingDataBean {

	private String auto_id;
	private String followup_id_server;
	private String meeting_no;
	private String meeting_date;
	private String no_of_participants;
	private String stakeholder_meeting;
	private String country_id;
	private String province_id;
	private String district_id;
	private String cycle_id;
	private String year;
	private String user_id;
	private String recordcreated;
	private String timestamp;
	private String datafrom;
	
	public Form5PartAStakesMeetingDataBean() {
		
	}
	public Form5PartAStakesMeetingDataBean(String auto_id, String followup_id_server, String meeting_no,
			String meeting_date, String no_of_participants, String stakeholder_meeting, String country_id,
			String province_id, String district_id, String cycle_id, String year, String user_id, String recordcreated,
			String timestamp, String datafrom) {
		
		this.auto_id = auto_id;
		this.followup_id_server = followup_id_server;
		this.meeting_no = meeting_no;
		this.meeting_date = meeting_date;
		this.no_of_participants = no_of_participants;
		this.stakeholder_meeting = stakeholder_meeting;
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
	public String getMeeting_no() {
		return meeting_no;
	}
	public void setMeeting_no(String meeting_no) {
		this.meeting_no = meeting_no;
	}
	public String getMeeting_date() {
		return meeting_date;
	}
	public void setMeeting_date(String meeting_date) {
		this.meeting_date = meeting_date;
	}
	public String getNo_of_participants() {
		return no_of_participants;
	}
	public void setNo_of_participants(String no_of_participants) {
		this.no_of_participants = no_of_participants;
	}
	public String getStakeholder_meeting() {
		return stakeholder_meeting;
	}
	public void setStakeholder_meeting(String stakeholder_meeting) {
		this.stakeholder_meeting = stakeholder_meeting;
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
		return "Form5PartAStakesMeetingDataBean [auto_id=" + auto_id + ", followup_id_server=" + followup_id_server
				+ ", meeting_no=" + meeting_no + ", meeting_date=" + meeting_date + ", no_of_participants="
				+ no_of_participants + ", stakeholder_meeting=" + stakeholder_meeting + ", country_id=" + country_id
				+ ", province_id=" + province_id + ", district_id=" + district_id + ", cycle_id=" + cycle_id + ", year="
				+ year + ", user_id=" + user_id + ", recordcreated=" + recordcreated + ", timestamp=" + timestamp
				+ ", datafrom=" + datafrom + "]";
	}
	
     
}
