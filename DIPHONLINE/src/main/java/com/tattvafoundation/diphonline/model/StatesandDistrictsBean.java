package com.tattvafoundation.diphonline.model;

import java.util.List;

public class StatesandDistrictsBean {
	
	private List<States> statesList; 
	private List<District> districtsList;
	
	public StatesandDistrictsBean() {		
	}
	
	public StatesandDistrictsBean(List<States> statesList, List<District> districtsList) {		
		this.statesList = statesList;
		this.districtsList = districtsList;
	}
	
	public List<States> getStatesList() {
		return statesList;
	}
	public void setStatesList(List<States> statesList) {
		this.statesList = statesList;
	}
	public List<District> getDistrictsList() {
		return districtsList;
	}
	public void setDistrictsList(List<District> districtsList) {
		this.districtsList = districtsList;
	}
	@Override
	public String toString() {
		return "StatesandDistrictsBean [statesList=" + statesList + ", districtsList=" + districtsList + "]";
	}
	
	
	
}
