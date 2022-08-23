package com.tattvafoundation.diphonline.indicator.entity;

public class Indicator {

	private int id;
	private String indicatorName;
	private String definition;
	private String numerator;
	private String denominator;
	private String source;
	private String frequency;
	private int areaId;
	private String category;
	
	public Indicator() {
		super();
	}

	public Indicator(int id, String indicatorName, String definition, String numerator, String denominator,
			String source, String frequency, int areaId, String category) {
		super();
		this.id = id;
		this.indicatorName = indicatorName;
		this.definition = definition;
		this.numerator = numerator;
		this.denominator = denominator;
		this.source = source;
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
		return "Indicator [id=" + id + ", indicatorName=" + indicatorName + ", definition=" + definition
				+ ", numerator=" + numerator + ", denominator=" + denominator + ", source=" + source + ", frequency="
				+ frequency + ", areaId=" + areaId + ", category=" + category + "]";
	}
	
}
