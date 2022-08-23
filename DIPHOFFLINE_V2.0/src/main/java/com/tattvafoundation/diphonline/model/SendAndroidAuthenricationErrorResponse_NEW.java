package com.tattvafoundation.diphonline.model;

import java.util.List;

public class SendAndroidAuthenricationErrorResponse_NEW implements I_SendAndroidAllForms {

	private List<District> districtList;
	private List<Cycle> cyclesList;
	private List<Year> yearList;
	private List<Areas_of_Indicators_List> areas_list;
	private String error_code;
	private String message;
	
	
	
	public SendAndroidAuthenricationErrorResponse_NEW() {
		
	}



	public SendAndroidAuthenricationErrorResponse_NEW(List<District> districtList, List<Cycle> cyclesList,
			List<Year> yearList, List<Areas_of_Indicators_List> areas_list, String error_code, String message) {
		
		this.districtList = districtList;
		this.cyclesList = cyclesList;
		this.yearList = yearList;
		this.areas_list = areas_list;
		this.error_code = error_code;
		this.message = message;
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



	public List<Areas_of_Indicators_List> getAreas_list() {
		return areas_list;
	}



	public void setAreas_list(List<Areas_of_Indicators_List> areas_list) {
		this.areas_list = areas_list;
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
		return "SendAndroidAuthenricationErrorResponse_NEW [districtList=" + districtList + ", cyclesList=" + cyclesList
				+ ", yearList=" + yearList + ", areas_list=" + areas_list + ", error_code=" + error_code + ", message="
				+ message + "]";
	}
	
	
}
