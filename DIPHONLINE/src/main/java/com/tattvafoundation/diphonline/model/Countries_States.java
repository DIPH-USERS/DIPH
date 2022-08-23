package com.tattvafoundation.diphonline.model;

import java.util.ArrayList;
import java.util.List;

public class Countries_States {

	private List<Country> countryList;
	private List<States> statesList;
	
	public List<Country> getCountryList() {

		if (countryList == null) {
			countryList = new ArrayList<>();
		}

		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public List<States> getStatesList() {

		if (statesList == null) {
			statesList = new ArrayList<>();
		}

		return statesList;
	}

	public void setStatesList(List<States> statesList) {
		this.statesList = statesList;
	}

}
