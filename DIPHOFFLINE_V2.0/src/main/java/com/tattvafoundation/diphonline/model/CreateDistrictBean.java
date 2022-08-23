package com.tattvafoundation.diphonline.model;

public class CreateDistrictBean {

	private String district_name;
	private String country_id;
	private String state_id;
	
	public CreateDistrictBean() {
		
	}

	public CreateDistrictBean(String district_name, String country_id, String state_id) {
		this.district_name = district_name;
		this.country_id = country_id;
		this.state_id = state_id;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	@Override
	public String toString() {
		return "CreateDistrictBean [district_name=" + district_name + ", country_id=" + country_id + ", state_id="
				+ state_id + "]";
	}
	
}
