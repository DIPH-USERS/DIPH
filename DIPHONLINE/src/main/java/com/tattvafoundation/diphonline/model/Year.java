package com.tattvafoundation.diphonline.model;

public class Year {

	private String year_id;
	private String year_name;
	
	public Year() {
		
	}

	public Year(String year_id, String year_name) {
		
		this.year_id = year_id;
		this.year_name = year_name;
	}

	public String getYear_id() {
		return year_id;
	}

	public void setYear_id(String year_id) {
		this.year_id = year_id;
	}

	public String getYear_name() {
		return year_name;
	}

	public void setYear_name(String year_name) {
		this.year_name = year_name;
	}

	@Override
	public String toString() {
		return "Year [year_id=" + year_id + ", year_name=" + year_name + "]";
	}
	
	
	
	
}
