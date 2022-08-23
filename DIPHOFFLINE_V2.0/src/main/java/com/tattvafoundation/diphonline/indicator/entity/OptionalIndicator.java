package com.tattvafoundation.diphonline.indicator.entity;

public class OptionalIndicator {

	private int id;
	private String indicatorName;
	private String definition;
	private String numerator;
	private String denominator;
	private String source;
	private String theme;
	private String currentAvailable;
	private String frequency;
	private int areaId;
	private String category;
	
	public OptionalIndicator() {
		super();
	}

	public OptionalIndicator(int id, String indicatorName, String definition, String numerator, String denominator,
			String source, String theme, String currentAvailable, String frequency, int areaId, String category) {
		super();
		this.id = id;
		this.indicatorName = indicatorName;
		this.definition = definition;
		this.numerator = numerator;
		this.denominator = denominator;
		this.source = source;
		this.theme = theme;
		this.currentAvailable = currentAvailable;
		this.frequency = frequency;
		this.areaId = areaId;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
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

	public String getCurrentAvailable() {
		return currentAvailable;
	}

	public void setCurrentAvailable(String currentAvailable) {
		this.currentAvailable = currentAvailable;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "OptionalIndicator [id=" + id + ", indicatorName=" + indicatorName + ", definition=" + definition
				+ ", numerator=" + numerator + ", denominator=" + denominator + ", source=" + source + ", theme="
				+ theme + ", currentAvailable=" + currentAvailable + ", frequency=" + frequency + ", areaId=" + areaId
				+ ", category=" + category + "]";
	}
	
		
}
