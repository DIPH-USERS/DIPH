package com.tattvafoundation.diphonline.indicator.entity;

public class AreaIndicatorMapping {

	private int id;
	private int indicatorId;
	private int areaId;
	
	public AreaIndicatorMapping() {
		super();
	}

	public AreaIndicatorMapping(int id, int indicatorId, int areaId) {
		super();
		this.id = id;
		this.indicatorId = indicatorId;
		this.areaId = areaId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "AreaIndicatorMapping [id=" + id + ", indicatorId=" + indicatorId + ", areaId=" + areaId + "]";
	}
	
	
}
