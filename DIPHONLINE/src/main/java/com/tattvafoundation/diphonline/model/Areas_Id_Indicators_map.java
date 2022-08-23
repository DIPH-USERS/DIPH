package com.tattvafoundation.diphonline.model;

import java.util.HashMap;
import java.util.List;

public class Areas_Id_Indicators_map {

	private HashMap<String,HashMap<String,List<String>>> mapdata;
	
	public Areas_Id_Indicators_map() {
		
	}

	public Areas_Id_Indicators_map(HashMap<String, HashMap<String, List<String>>> mapdata) {
		this.mapdata = mapdata;
	}

	public HashMap<String, HashMap<String, List<String>>> getMapdata() {
		return mapdata;
	}

	public void setMapdata(HashMap<String, HashMap<String, List<String>>> mapdata) {
		this.mapdata = mapdata;
	}	
	
}


