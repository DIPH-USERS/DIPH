package com.tattvafoundation.diphonline.model;

import java.util.List;

public class DistrictList_CyclesList {
	
	private List<District> districtList;
	private List<Cycle> cyclesList;

	public DistrictList_CyclesList() {

	}

	public DistrictList_CyclesList(List<District> districtList, List<Cycle> cyclesList) {
		this.districtList = districtList;
		this.cyclesList = cyclesList;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}

	public List<Cycle> getCyclesList() {
		return cyclesList;
	}

	public void setCyclesList(List<Cycle> cyclesList) {
		this.cyclesList = cyclesList;
	}

	@Override
	public String toString() {
		return "DistrictList_CyclesList [districtList=" + districtList + ", cyclesList=" + cyclesList + "]";
	}
	
}
