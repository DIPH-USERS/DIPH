package com.tattvafoundation.diphonline.model;

import java.util.List;

public class UserDistrictCycleYearList {

	private List<District> districtList;
	private List<Cycle> cyclesList;
	private List<Year> yearList;
	
	public UserDistrictCycleYearList() {
		
	}

	public UserDistrictCycleYearList(List<District> districtList, List<Cycle> cyclesList, List<Year> yearList) {
		
		this.districtList = districtList;
		this.cyclesList = cyclesList;
		this.yearList = yearList;
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

	public List<Year> getYearList() {
		return yearList;
	}

	public void setYearList(List<Year> yearList) {
		this.yearList = yearList;
	}

	@Override
	public String toString() {
		return "UserDistrictCycleYearList [districtList=" + districtList + ", cyclesList=" + cyclesList + ", yearList="
				+ yearList + "]";
	}
	
	
	
}
