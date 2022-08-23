package com.tattvafoundation.diphonline.model;

public class FeedbackDetailsBean {
	
	private String username;	
	private String country_n;	
	private String region_n;	
	private String state_n;
	private String district_n;
	private String email;
	private String subject_n;
	private String feedback_desc;
	private String selectedcountry;
	private String selecteddistrict;
	private String registeredUser;	
	
	public FeedbackDetailsBean() {
		
		
	}

	public FeedbackDetailsBean(String username, String country_n, String region_n, String state_n, String district_n,
			String email, String subject_n, String feedback_desc, String selectedcountry, String selecteddistrict,
			String registeredUser) {
		
		this.username = username;
		this.country_n = country_n;
		this.region_n = region_n;
		this.state_n = state_n;
		this.district_n = district_n;
		this.email = email;
		this.subject_n = subject_n;
		this.feedback_desc = feedback_desc;
		this.selectedcountry = selectedcountry;
		this.selecteddistrict = selecteddistrict;
		this.registeredUser = registeredUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCountry_n() {
		return country_n;
	}

	public void setCountry_n(String country_n) {
		this.country_n = country_n;
	}

	public String getRegion_n() {
		return region_n;
	}

	public void setRegion_n(String region_n) {
		this.region_n = region_n;
	}

	public String getState_n() {
		return state_n;
	}

	public void setState_n(String state_n) {
		this.state_n = state_n;
	}

	public String getDistrict_n() {
		return district_n;
	}

	public void setDistrict_n(String district_n) {
		this.district_n = district_n;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject_n() {
		return subject_n;
	}

	public void setSubject_n(String subject_n) {
		this.subject_n = subject_n;
	}

	public String getFeedback_desc() {
		return feedback_desc;
	}

	public void setFeedback_desc(String feedback_desc) {
		this.feedback_desc = feedback_desc;
	}

	public String getSelectedcountry() {
		return selectedcountry;
	}

	public void setSelectedcountry(String selectedcountry) {
		this.selectedcountry = selectedcountry;
	}

	public String getSelecteddistrict() {
		return selecteddistrict;
	}

	public void setSelecteddistrict(String selecteddistrict) {
		this.selecteddistrict = selecteddistrict;
	}

	public String getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(String registeredUser) {
		this.registeredUser = registeredUser;
	}

	@Override
	public String toString() {
		return "FeedbackDetailsBean [username=" + username + ", country_n=" + country_n + ", region_n=" + region_n
				+ ", state_n=" + state_n + ", district_n=" + district_n + ", email=" + email + ", subject_n="
				+ subject_n + ", feedback_desc=" + feedback_desc + ", selectedcountry=" + selectedcountry
				+ ", selecteddistrict=" + selecteddistrict + ", registeredUser=" + registeredUser + "]";
	}

		
}
