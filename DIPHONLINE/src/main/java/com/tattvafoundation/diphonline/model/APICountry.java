package com.tattvafoundation.diphonline.model;

public class APICountry {

	private String country_id;
	private String region_id;

	public APICountry() {

	}

	public APICountry(String country_id, String region_id) {
		this.country_id = country_id;
		this.region_id = region_id;
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

	@Override
	public String toString() {
		return "APICountry [country_id=" + country_id + ", region_id=" + region_id + "]";
	}
	
	
}
