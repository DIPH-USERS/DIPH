package com.tattvafoundation.diphonline.model;

public class Form5FollowUpIndicatorsInfo {

	private String indicator_val;
	private String indicator_id;
	private String area_name;
	private String area_id;
	private String target;
	private String progress_indicators;
	private String dev_indicator_id;
	
	public Form5FollowUpIndicatorsInfo() {
		
	}

	public Form5FollowUpIndicatorsInfo(String indicator_val, String indicator_id, String area_name, String area_id,
			String target, String progress_indicators, String dev_indicator_id) {
		this.indicator_val = indicator_val;
		this.indicator_id = indicator_id;
		this.area_name = area_name;
		this.area_id = area_id;
		this.target = target;
		this.progress_indicators = progress_indicators;
		this.dev_indicator_id = dev_indicator_id;
	}

	public String getIndicator_val() {
		return indicator_val;
	}

	public void setIndicator_val(String indicator_val) {
		this.indicator_val = indicator_val;
	}

	public String getIndicator_id() {
		return indicator_id;
	}

	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getProgress_indicators() {
		return progress_indicators;
	}

	public void setProgress_indicators(String progress_indicators) {
		this.progress_indicators = progress_indicators;
	}

	public String getDev_indicator_id() {
		return dev_indicator_id;
	}

	public void setDev_indicator_id(String dev_indicator_id) {
		this.dev_indicator_id = dev_indicator_id;
	}

	@Override
	public String toString() {
		return "Form5FollowUpIndicatorsInfo [indicator_val=" + indicator_val + ", indicator_id=" + indicator_id
				+ ", area_name=" + area_name + ", area_id=" + area_id + ", target=" + target + ", progress_indicators="
				+ progress_indicators + ", dev_indicator_id=" + dev_indicator_id + "]";
	}
	
	
	
	
}
