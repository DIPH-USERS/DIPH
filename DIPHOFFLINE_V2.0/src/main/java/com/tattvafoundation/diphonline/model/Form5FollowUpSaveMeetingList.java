package com.tattvafoundation.diphonline.model;

public class Form5FollowUpSaveMeetingList {
	private String meeting_no;
	private String meeting_date;
	private String stakeholder_meeting;
	private String no_of_participants;
	private String insert;
	
	
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
	public String getStakeholder_meeting() {
		return stakeholder_meeting;
	}
	public void setStakeholder_meeting(String stakeholder_meeting) {
		this.stakeholder_meeting = stakeholder_meeting;
	}
	public String getNo_of_participants() {
		return no_of_participants;
	}
	public void setNo_of_participants(String no_of_participants) {
		this.no_of_participants = no_of_participants;
	}
	public String getInsert() {
		return insert;
	}
	public void setInsert(String insert) {
		this.insert = insert;
	}
	@Override
	public String toString() {
		return "Form5FollowUpSaveMeetingList [meeting_no=" + meeting_no + ", meeting_date=" + meeting_date
				+ ", stakeholder_meeting=" + stakeholder_meeting + ", no_of_participants=" + no_of_participants
				+ ", insert=" + insert + "]";
	}
	
		
}
