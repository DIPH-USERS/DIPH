package com.tattvafoundation.diphonline.controller;

import java.util.List;

public class OptionalindicatorsList {

	public List<OptionalindicatorBean> optional_indicators_list;
	
	

	public OptionalindicatorsList() {
		
	}



	public OptionalindicatorsList(List<OptionalindicatorBean> optional_indicators_list) {
		
		this.optional_indicators_list = optional_indicators_list;
	}



	public List<OptionalindicatorBean> getOptional_indicators_list() {
		return optional_indicators_list;
	}



	public void setOptional_indicators_list(List<OptionalindicatorBean> optional_indicators_list) {
		this.optional_indicators_list = optional_indicators_list;
	}



	@Override
	public String toString() {
		return "OptionalindicatorsList [optional_indicators_list=" + optional_indicators_list + "]";
	}
	
	
}
