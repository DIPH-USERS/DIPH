package com.tattvafoundation.diphonline.controller;

public class OptionalindicatorBean {
	
	private String indicator_id;
	private String indicator_val;
	private String definition;
	private String numerator;
	private String denominator;
	private String source;
	private String theme;
	private String current_available;
	private String frequency;
	private String area_id;
	private String category;
	private String area_name;
	
	public OptionalindicatorBean() {
		
	}

	public OptionalindicatorBean(String indicator_id, String indicator_val, String definition, String numerator,
			String denominator, String source, String theme, String current_available, String frequency, String area_id,
			String category, String area_name) {		
		this.indicator_id = indicator_id;
		this.indicator_val = indicator_val;
		this.definition = definition;
		this.numerator = numerator;
		this.denominator = denominator;
		this.source = source;
		this.theme = theme;
		this.current_available = current_available;
		this.frequency = frequency;
		this.area_id = area_id;
		this.category = category;
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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCurrent_available() {
		return current_available;
	}

	public void setCurrent_available(String current_available) {
		this.current_available = current_available;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	@Override
	public String toString() {
		return "OptionalindicatorBean [indicator_id=" + indicator_id + ", indicator_val=" + indicator_val
				+ ", definition=" + definition + ", numerator=" + numerator + ", denominator=" + denominator
				+ ", source=" + source + ", theme=" + theme + ", current_available=" + current_available
				+ ", frequency=" + frequency + ", area_id=" + area_id + ", category=" + category + ", area_name="
				+ area_name + "]";
	}

	
}
