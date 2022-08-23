package com.tattvafoundation.diphonline.model;

import java.util.ArrayList;
import java.util.List;

public class S_Districts {

	private List<District> districtList;

	public S_Districts() {

	}
	public S_Districts(List<District> districtList) {
		this.districtList = districtList;
	}

	public List<District> getDistrictsList() {

		if (districtList == null) {
			districtList = new ArrayList<>();
		}

		return districtList;
	}

	public void setDistrictsList(List<District> districtList) {
		this.districtList = districtList;
	}

}
