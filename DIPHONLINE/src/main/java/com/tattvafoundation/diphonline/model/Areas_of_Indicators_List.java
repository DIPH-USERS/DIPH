package com.tattvafoundation.diphonline.model;

public class Areas_of_Indicators_List {

	String id;
	String area_name;
	
	public Areas_of_Indicators_List() {
		
	}

	public Areas_of_Indicators_List(String id, String area_name) {
		this.id = id;
		this.area_name = area_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	@Override
	public String toString() {
		return "Areas_of_Indicators_List [id=" + id + ", area_name=" + area_name + "]";
	}
	
}
