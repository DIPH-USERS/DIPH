package com.tattvafoundation.diphonline.model;

public class States_App {

	private long cs_id;
	private int country_id;
	private String state_name;
	private String status;
	
	public States_App() {
		
	}

	public States_App(long cs_id, int country_id, String state_name, String status) {
		this.cs_id = cs_id;
		this.country_id = country_id;
		this.state_name = state_name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "States_App [cs_id=" + cs_id + ", country_id=" + country_id + ", state_name=" + state_name + ", status="
				+ status + "]";
	}
	
	
}
