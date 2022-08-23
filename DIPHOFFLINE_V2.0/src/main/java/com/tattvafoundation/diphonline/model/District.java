package com.tattvafoundation.diphonline.model;

public class District {

	//district_id,country_id,district_name
	private int district_id;
	private int country_id;
	private String district_name;
	private String state_id;
	private String region_id;
	
	public District() {
		
	}

	public District(int district_id, int country_id, String district_name, String state_id, String region_id) {
		this.district_id = district_id;
		this.country_id = country_id;
		this.district_name = district_name;
		this.state_id = state_id;
		this.region_id = region_id;
	}

	public int getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	@Override
	public String toString() {
		return "District [district_id=" + district_id + ", country_id=" + country_id + ", district_name="
				+ district_name + ", state_id=" + state_id + ", region_id=" + region_id + "]";
	}
	
	
	

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + country_id;
//		result = prime * result + district_id;
//		result = prime * result + ((district_name == null) ? 0 : district_name.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		District other = (District) obj;
//		if (country_id != other.country_id)
//			return false;
//		if (district_id != other.district_id)
//			return false;
//		if (district_name == null) {
//			if (other.district_name != null)
//				return false;
//		} else if (!district_name.equals(other.district_name))
//			return false;
//		return true;
//	}
	
	
	
}
