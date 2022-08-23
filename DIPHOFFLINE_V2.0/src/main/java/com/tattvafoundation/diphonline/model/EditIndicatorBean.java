package com.tattvafoundation.diphonline.model;

public class EditIndicatorBean {

	private String edit_indicator_area_id;
	private String edit_indicator_name;
	private String edit_indicator_desc;
	private String edit_indicator_numerator;
	private String edit_indicator_denominator;
	private String edit_indicator_source;
	private String edit_indicator_frequency;
	private String edit_indicator_category;
	private String indicator_id;


	public EditIndicatorBean() {
	}


	public EditIndicatorBean(String edit_indicator_area_id, String edit_indicator_name, String edit_indicator_desc,
			String edit_indicator_numerator, String edit_indicator_denominator, String edit_indicator_source,
			String edit_indicator_frequency, String edit_indicator_category, String indicator_id) {
		this.edit_indicator_area_id = edit_indicator_area_id;
		this.edit_indicator_name = edit_indicator_name;
		this.edit_indicator_desc = edit_indicator_desc;
		this.edit_indicator_numerator = edit_indicator_numerator;
		this.edit_indicator_denominator = edit_indicator_denominator;
		this.edit_indicator_source = edit_indicator_source;
		this.edit_indicator_frequency = edit_indicator_frequency;
		this.edit_indicator_category = edit_indicator_category;
		this.indicator_id = indicator_id;
	}


	public String getEdit_indicator_area_id() {
		return edit_indicator_area_id;
	}


	public void setEdit_indicator_area_id(String edit_indicator_area_id) {
		this.edit_indicator_area_id = edit_indicator_area_id;
	}


	public String getEdit_indicator_name() {
		return edit_indicator_name;
	}


	public void setEdit_indicator_name(String edit_indicator_name) {
		this.edit_indicator_name = edit_indicator_name;
	}


	public String getEdit_indicator_desc() {
		return edit_indicator_desc;
	}


	public void setEdit_indicator_desc(String edit_indicator_desc) {
		this.edit_indicator_desc = edit_indicator_desc;
	}


	public String getEdit_indicator_numerator() {
		return edit_indicator_numerator;
	}


	public void setEdit_indicator_numerator(String edit_indicator_numerator) {
		this.edit_indicator_numerator = edit_indicator_numerator;
	}


	public String getEdit_indicator_denominator() {
		return edit_indicator_denominator;
	}


	public void setEdit_indicator_denominator(String edit_indicator_denominator) {
		this.edit_indicator_denominator = edit_indicator_denominator;
	}


	public String getEdit_indicator_source() {
		return edit_indicator_source;
	}


	public void setEdit_indicator_source(String edit_indicator_source) {
		this.edit_indicator_source = edit_indicator_source;
	}


	public String getEdit_indicator_frequency() {
		return edit_indicator_frequency;
	}


	public void setEdit_indicator_frequency(String edit_indicator_frequency) {
		this.edit_indicator_frequency = edit_indicator_frequency;
	}


	public String getEdit_indicator_category() {
		return edit_indicator_category;
	}


	public void setEdit_indicator_category(String edit_indicator_category) {
		this.edit_indicator_category = edit_indicator_category;
	}


	public String getIndicator_id() {
		return indicator_id;
	}


	public void setIndicator_id(String indicator_id) {
		this.indicator_id = indicator_id;
	}


	@Override
	public String toString() {
		return "EditIndicatorBean [edit_indicator_area_id=" + edit_indicator_area_id + ", edit_indicator_name="
				+ edit_indicator_name + ", edit_indicator_desc=" + edit_indicator_desc + ", edit_indicator_numerator="
				+ edit_indicator_numerator + ", edit_indicator_denominator=" + edit_indicator_denominator
				+ ", edit_indicator_source=" + edit_indicator_source + ", edit_indicator_frequency="
				+ edit_indicator_frequency + ", edit_indicator_category=" + edit_indicator_category + ", indicator_id="
				+ indicator_id + "]";
	}

	
}
