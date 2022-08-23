package com.tattvafoundation.diphonline.model;

import java.util.List;

public class APPAllDistrictsAllCyclesBean {
	
	private List<States> statesList;
	private List<District> districtList;
	private List<Cycle> cyclesList;
	private List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list;
	private List<Areas_of_Indicators_List> areas_list;
	private List<VerifiedByNameBean> verified_by_name;
	private String error_code;
	private String message;
	
	public APPAllDistrictsAllCyclesBean() {
		
	}

	public APPAllDistrictsAllCyclesBean(List<States> statesList, List<District> districtList, List<Cycle> cyclesList,
			List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list, List<Areas_of_Indicators_List> areas_list,
			List<VerifiedByNameBean> verified_by_name, String error_code, String message) {
		this.statesList = statesList;
		this.districtList = districtList;
		this.cyclesList = cyclesList;
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
		this.areas_list = areas_list;
		this.verified_by_name = verified_by_name;
		this.error_code = error_code;
		this.message = message;
	}

	public List<States> getStatesList() {
		return statesList;
	}

	public void setStatesList(List<States> statesList) {
		this.statesList = statesList;
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

	public List<Menu_Area_Indicator_Object> getAreas_Id_Indicators_map_list() {
		return areas_Id_Indicators_map_list;
	}

	public void setAreas_Id_Indicators_map_list(List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list) {
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
	}

	public List<Areas_of_Indicators_List> getAreas_list() {
		return areas_list;
	}

	public void setAreas_list(List<Areas_of_Indicators_List> areas_list) {
		this.areas_list = areas_list;
	}

	public List<VerifiedByNameBean> getVerified_by_name() {
		return verified_by_name;
	}

	public void setVerified_by_name(List<VerifiedByNameBean> verified_by_name) {
		this.verified_by_name = verified_by_name;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "APPAllDistrictsAllCyclesBean [statesList=" + statesList + ", districtList=" + districtList
				+ ", cyclesList=" + cyclesList + ", areas_Id_Indicators_map_list=" + areas_Id_Indicators_map_list
				+ ", areas_list=" + areas_list + ", verified_by_name=" + verified_by_name + ", error_code=" + error_code
				+ ", message=" + message + "]";
	}

	
}


