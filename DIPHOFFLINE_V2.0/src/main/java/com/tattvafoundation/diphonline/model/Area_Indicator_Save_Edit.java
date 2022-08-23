package com.tattvafoundation.diphonline.model;

public class Area_Indicator_Save_Edit {

	private String area_id;
	private String area_name;
	private String indicator_id;
	private String indicator_val;
	private String data;
	private String expected;
	private String gap;
	private String source;
	private String indicator_desc;

	public Area_Indicator_Save_Edit() {

	}

	public Area_Indicator_Save_Edit(String area_id, String area_name, String indicator_id, String indicator_val,
			String data, String expected, String gap, String source, String indicator_desc) {
		this.area_id = area_id;
		this.area_name = area_name;
		this.indicator_id = indicator_id;
		this.indicator_val = indicator_val;
		this.data = data;
		this.expected = expected;
		this.gap = gap;
		this.source = source;
		this.indicator_desc = indicator_desc;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getIndicator_id() {
		return indicator_id;
	}

	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}

	public String getIndicator_val() {
		return indicator_val;
	}

	public void setIndicator_val(String indicator_val) {
		this.indicator_val = indicator_val;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getGap() {
		return gap;
	}

	public void setGap(String gap) {
		this.gap = gap;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}	
	
	public String getIndicator_desc() {
		return indicator_desc;
	}

	public void setIndicator_desc(String indicator_desc) {
		this.indicator_desc = indicator_desc;
	}

	@Override
	public String toString() {
		return "Area_Indicator_Save_Edit [area_id=" + area_id + ", area_name=" + area_name + ", indicator_id="
				+ indicator_id + ", indicator_val=" + indicator_val + ", data=" + data + ", expected=" + expected
				+ ", gap=" + gap + ", source=" + source + ", indicator_desc=" + indicator_desc + "]";
	}

}
