package com.tattvafoundation.diphonline.model;

public class States {

	private long cs_id;
	private int country_id;
	private String state_name;
	private String region_id;
	private String status;

	public States() {

	}

	public States(long cs_id, int country_id, String state_name, String region_id, String status) {
		this.cs_id = cs_id;
		this.country_id = country_id;
		this.state_name = state_name;
		this.region_id = region_id;
		this.status = status;
	}

	public long getCs_id() {
		return cs_id;
	}

	public void setCs_id(long cs_id) {
		this.cs_id = cs_id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "States [cs_id=" + cs_id + ", country_id=" + country_id + ", state_name=" + state_name + ", region_id="
				+ region_id + ", status=" + status + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + country_id;
//		result = prime * result + (int) (cs_id ^ (cs_id >>> 32));
//		result = prime * result + ((state_name == null) ? 0 : state_name.hashCode());
//		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
//		States other = (States) obj;
//		if (country_id != other.country_id)
//			return false;
//		if (cs_id != other.cs_id)
//			return false;
//		if (state_name == null) {
//			if (other.state_name != null)
//				return false;
//		} else if (!state_name.equals(other.state_name))
//			return false;
//		if (status == null) {
//			if (other.status != null)
//				return false;
//		} else if (!status.equals(other.status))
//			return false;
//		return true;
//	}
	
	

}
