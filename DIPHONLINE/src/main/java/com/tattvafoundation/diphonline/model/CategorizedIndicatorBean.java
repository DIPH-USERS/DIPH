package com.tattvafoundation.diphonline.model;

public class CategorizedIndicatorBean {

	private String new_indicator_area_id;
	private String new_area_name;
	private String new_indicator_name;
	private String new_indicator_desc;
	private String new_indicator_numerator;
	private String new_indicator_denominator;
	private String new_indicator_source;
	private String new_indicator_frequency;
	private String new_indicator_type;
	
	public CategorizedIndicatorBean() {		
	}

	public CategorizedIndicatorBean(String new_indicator_area_id, String new_area_name, String new_indicator_name,
			String new_indicator_desc, String new_indicator_numerator, String new_indicator_denominator,
			String new_indicator_source, String new_indicator_frequency, String new_indicator_type) {
		this.new_indicator_area_id = new_indicator_area_id;
		this.new_area_name = new_area_name;
		this.new_indicator_name = new_indicator_name;
		this.new_indicator_desc = new_indicator_desc;
		this.new_indicator_numerator = new_indicator_numerator;
		this.new_indicator_denominator = new_indicator_denominator;
		this.new_indicator_source = new_indicator_source;
		this.new_indicator_frequency = new_indicator_frequency;
		this.new_indicator_type = new_indicator_type;
	}

	public String getNew_indicator_area_id() {
		return new_indicator_area_id;
	}

	public void setNew_indicator_area_id(String new_indicator_area_id) {
		this.new_indicator_area_id = new_indicator_area_id;
	}

	public String getNew_area_name() {
		return new_area_name;
	}

	public void setNew_area_name(String new_area_name) {
		this.new_area_name = new_area_name;
	}

	public String getNew_indicator_name() {
		return new_indicator_name;
	}

	public void setNew_indicator_name(String new_indicator_name) {
		this.new_indicator_name = new_indicator_name;
	}

	public String getNew_indicator_desc() {
		return new_indicator_desc;
	}

	public void setNew_indicator_desc(String new_indicator_desc) {
		this.new_indicator_desc = new_indicator_desc;
	}

	public String getNew_indicator_numerator() {
		return new_indicator_numerator;
	}

	public void setNew_indicator_numerator(String new_indicator_numerator) {
		this.new_indicator_numerator = new_indicator_numerator;
	}

	public String getNew_indicator_denominator() {
		return new_indicator_denominator;
	}

	public void setNew_indicator_denominator(String new_indicator_denominator) {
		this.new_indicator_denominator = new_indicator_denominator;
	}

	public String getNew_indicator_source() {
		return new_indicator_source;
	}

	public void setNew_indicator_source(String new_indicator_source) {
		this.new_indicator_source = new_indicator_source;
	}

	public String getNew_indicator_frequency() {
		return new_indicator_frequency;
	}

	public void setNew_indicator_frequency(String new_indicator_frequency) {
		this.new_indicator_frequency = new_indicator_frequency;
	}

	public String getNew_indicator_type() {
		return new_indicator_type;
	}

	public void setNew_indicator_type(String new_indicator_type) {
		this.new_indicator_type = new_indicator_type;
	}

	@Override
	public String toString() {
		return "CategorizedIndicatorBean [new_indicator_area_id=" + new_indicator_area_id + ", new_area_name="
				+ new_area_name + ", new_indicator_name=" + new_indicator_name + ", new_indicator_desc="
				+ new_indicator_desc + ", new_indicator_numerator=" + new_indicator_numerator
				+ ", new_indicator_denominator=" + new_indicator_denominator + ", new_indicator_source="
				+ new_indicator_source + ", new_indicator_frequency=" + new_indicator_frequency
				+ ", new_indicator_type=" + new_indicator_type + "]";
	}
	
	
}
