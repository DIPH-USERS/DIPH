package com.tattvafoundation.diphonline.indicator.entity;

public class Area {

	private int id;
	private String areaName;
	
	public Area() {
		
	}
	
	public Area(int id, String areaName) {
		super();
		this.id = id;
		this.areaName = areaName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", areaName=" + areaName + "]";
	}
	
	
}
