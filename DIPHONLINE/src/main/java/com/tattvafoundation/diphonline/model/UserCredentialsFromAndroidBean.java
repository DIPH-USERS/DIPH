package com.tattvafoundation.diphonline.model;

public class UserCredentialsFromAndroidBean {

	private String country_id;
	private String region_id;
	private String province_id;
	private String district_id;
	private String user_id;
	private String loggedinUserId;
	
	
	public UserCredentialsFromAndroidBean() {
		
	}
	
	


	public UserCredentialsFromAndroidBean(String country_id, String region_id, String province_id, String district_id,
			String user_id, String loggedinUserId) {
		this.country_id = country_id;
		this.region_id = region_id;
		this.province_id = province_id;
		this.district_id = district_id;
		this.user_id = user_id;
		this.loggedinUserId = loggedinUserId;
	}




	public String getCountry_id() {
		return country_id;
	}


	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}


	public String getRegion_id() {
		return region_id;
	}


	public void setRegion_id(String region_id) {
		this.region_id = region_id;
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


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getLoggedinUserId() {
		return loggedinUserId;
	}


	public void setLoggedinUserId(String loggedinUserId) {
		this.loggedinUserId = loggedinUserId;
	}




	@Override
	public String toString() {
		return "UserCredentialsFromAndroidBean [country_id=" + country_id + ", region_id=" + region_id
				+ ", province_id=" + province_id + ", district_id=" + district_id + ", user_id=" + user_id
				+ ", loggedinUserId=" + loggedinUserId + "]";
	}


	
	
}
