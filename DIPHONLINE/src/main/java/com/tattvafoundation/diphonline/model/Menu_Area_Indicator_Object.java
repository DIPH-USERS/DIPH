package com.tattvafoundation.diphonline.model;

public class Menu_Area_Indicator_Object {

	private String area_id;
	private String area_name;
	private String indicator_val;
	private String indicator_id;
	private String definition;
	private String numerator;
	private String denominator;
	private String source;
	private String frequency;
	private String category;

	public Menu_Area_Indicator_Object() {

	}

	public Menu_Area_Indicator_Object(String area_id, String area_name, String indicator_val, String indicator_id,
			String definition, String numerator, String denominator, String source, String frequency, String category) {
		this.area_id = area_id;
		this.area_name = area_name;
		this.indicator_val = indicator_val;
		this.indicator_id = indicator_id;
		this.definition = definition;
		this.numerator = numerator;
		this.denominator = denominator;
		this.source = source;
		this.frequency = frequency;
		this.category = category;
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

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getNumerator() {
		return numerator;
	}

	public void setNumerator(String numerator) {
		this.numerator = numerator;
	}

	public String getDenominator() {
		return denominator;
	}

	public void setDenominator(String denominator) {
		this.denominator = denominator;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Menu_Area_Indicator_Object [area_id=" + area_id + ", area_name=" + area_name + ", indicator_val="
				+ indicator_val + ", indicator_id=" + indicator_id + ", definition=" + definition + ", numerator="
				+ numerator + ", denominator=" + denominator + ", source=" + source + ", frequency=" + frequency
				+ ", category=" + category + "]";
	}
	
	

}
