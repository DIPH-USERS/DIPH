package com.tattvafoundation.diphonline.model;

public class APPLoginPostParameterBean_NEW {

	private String country_id;
	private String region_id;
	private String province_id;
	private String district_id;
	private String cycle;
	private String year;
	private String username;
	private String password;
	
	
	
	public APPLoginPostParameterBean_NEW() {		
	}



	public APPLoginPostParameterBean_NEW(String country_id, String region_id, String province_id, String district_id,
			String cycle, String year, String username, String password) {		
		this.country_id = country_id;
		this.region_id = region_id;
		this.province_id = province_id;
		this.district_id = district_id;
		this.cycle = cycle;
		this.year = year;
		this.username = username;
		this.password = password;
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



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "APPLoginPostParameterBean_NEW [country_id=" + country_id + ", region_id=" + region_id + ", province_id="
				+ province_id + ", district_id=" + district_id + ", cycle=" + cycle + ", year=" + year + ", username="
				+ username + ", password=" + password + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country_id == null) ? 0 : country_id.hashCode());
		result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
		result = prime * result + ((district_id == null) ? 0 : district_id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((province_id == null) ? 0 : province_id.hashCode());
		result = prime * result + ((region_id == null) ? 0 : region_id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		APPLoginPostParameterBean_NEW other = (APPLoginPostParameterBean_NEW) obj;
		if (country_id == null) {
			if (other.country_id != null)
				return false;
		} else if (!country_id.equals(other.country_id))
			return false;
		if (cycle == null) {
			if (other.cycle != null)
				return false;
		} else if (!cycle.equals(other.cycle))
			return false;
		if (district_id == null) {
			if (other.district_id != null)
				return false;
		} else if (!district_id.equals(other.district_id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (province_id == null) {
			if (other.province_id != null)
				return false;
		} else if (!province_id.equals(other.province_id))
			return false;
		if (region_id == null) {
			if (other.region_id != null)
				return false;
		} else if (!region_id.equals(other.region_id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
	
	
}
