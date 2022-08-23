package com.tattvafoundation.diphonline.model;

import java.util.List;

public class IndicatorsList {

	private List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list;
	
	public IndicatorsList() {
		
	}

	public IndicatorsList(List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list) {
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
	}

	public List<Menu_Area_Indicator_Object> getAreas_Id_Indicators_map_list() {
		return areas_Id_Indicators_map_list;
	}

	public void setAreas_Id_Indicators_map_list(List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list) {
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
	}

	@Override
	public String toString() {
		return "IndicatorsList [areas_Id_Indicators_map_list=" + areas_Id_Indicators_map_list + "]";
	}
	
}
