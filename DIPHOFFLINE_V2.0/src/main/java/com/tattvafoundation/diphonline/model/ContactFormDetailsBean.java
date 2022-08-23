package com.tattvafoundation.diphonline.model;

public class ContactFormDetailsBean {
	
	private String username;
	private String country_n;
	private String district_n;
	private String organization_n;
	private String address_n;
	private String mobile_n;
	private String email;
	private String subject_n;
	private String message_desc;
	private String selectedcountry;
	private String selecteddistrict;
	private String registeredUser;
	
	public ContactFormDetailsBean() {
		
	}

	public ContactFormDetailsBean(String username, String country_n, String district_n, String organization_n,
			String address_n, String mobile_n, String email, String subject_n, String message_desc,
			String selectedcountry, String selecteddistrict, String registeredUser) {
		
		this.username = username;
		this.country_n = country_n;
		this.district_n = district_n;
		this.organization_n = organization_n;
		this.address_n = address_n;
		this.mobile_n = mobile_n;
		this.email = email;
		this.subject_n = subject_n;
		this.message_desc = message_desc;
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

	public String getDistrict_n() {
		return district_n;
	}

	public void setDistrict_n(String district_n) {
		this.district_n = district_n;
	}

	public String getOrganization_n() {
		return organization_n;
	}

	public void setOrganization_n(String organization_n) {
		this.organization_n = organization_n;
	}

	public String getAddress_n() {
		return address_n;
	}

	public void setAddress_n(String address_n) {
		this.address_n = address_n;
	}

	public String getMobile_n() {
		return mobile_n;
	}

	public void setMobile_n(String mobile_n) {
		this.mobile_n = mobile_n;
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

	public String getMessage_desc() {
		return message_desc;
	}

	public void setMessage_desc(String message_desc) {
		this.message_desc = message_desc;
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
		return "ContactFormDetailsBean [username=" + username + ", country_n=" + country_n + ", district_n="
				+ district_n + ", organization_n=" + organization_n + ", address_n=" + address_n + ", mobile_n="
				+ mobile_n + ", email=" + email + ", subject_n=" + subject_n + ", message_desc=" + message_desc
				+ ", selectedcountry=" + selectedcountry + ", selecteddistrict=" + selecteddistrict
				+ ", registeredUser=" + registeredUser + "]";
	}

	
}
