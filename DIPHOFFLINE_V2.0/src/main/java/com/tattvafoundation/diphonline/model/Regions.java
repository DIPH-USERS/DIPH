package com.tattvafoundation.diphonline.model;

public class Regions {

	private long region_id;
	private int country_id;
	private String region_name;
	private String status;
	private String reproductive_age_women_cfactor;
	private String under_five_children_cfactor;
	
	
	public Regions() {
		
	}


	
	
	public Regions(long region_id, int country_id, String region_name, String status,
			String reproductive_age_women_cfactor, String under_five_children_cfactor) {
		super();
		this.region_id = region_id;
		this.country_id = country_id;
		this.region_name = region_name;
		this.status = status;
		this.reproductive_age_women_cfactor = reproductive_age_women_cfactor;
		this.under_five_children_cfactor = under_five_children_cfactor;
	}




	public long getRegion_id() {
		return region_id;
	}


	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}


	public int getCountry_id() {
		return country_id;
	}


	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}


	public String getRegion_name() {
		return region_name;
	}


	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getReproductive_age_women_cfactor() {
		return reproductive_age_women_cfactor;
	}


	public void setReproductive_age_women_cfactor(String reproductive_age_women_cfactor) {
		this.reproductive_age_women_cfactor = reproductive_age_women_cfactor;
	}


	public String getUnder_five_children_cfactor() {
		return under_five_children_cfactor;
	}


	public void setUnder_five_children_cfactor(String under_five_children_cfactor) {
		this.under_five_children_cfactor = under_five_children_cfactor;
	}




	@Override
	public String toString() {
		return "Regions [region_id=" + region_id + ", country_id=" + country_id + ", region_name=" + region_name
				+ ", status=" + status + ", reproductive_age_women_cfactor=" + reproductive_age_women_cfactor
				+ ", under_five_children_cfactor=" + under_five_children_cfactor + "]";
	}

	
	
}
