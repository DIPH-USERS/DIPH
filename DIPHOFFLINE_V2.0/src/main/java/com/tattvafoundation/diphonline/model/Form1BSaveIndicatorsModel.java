package com.tattvafoundation.diphonline.model;

public class Form1BSaveIndicatorsModel {
	
	private String area_id;
	private String area_name;
	private String indicator_val;
	private String indicator_id;
	private String data;
	private String gap;
	private String expected;
	private String source;
	private String ci_sl_no;
	private String indicator_desc;
	
	public Form1BSaveIndicatorsModel() {
		
	}

	public Form1BSaveIndicatorsModel(String area_id, String area_name, String indicator_val, String indicator_id,
			String data, String gap, String expected, String source, String ci_sl_no, String indicator_desc) {
		
		this.area_id = area_id;
		this.area_name = area_name;
		this.indicator_val = indicator_val;
		this.indicator_id = indicator_id;
		this.data = data;
		this.gap = gap;
		this.expected = expected;
		this.source = source;
		this.ci_sl_no = ci_sl_no;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGap() {
		return gap;
	}

	public void setGap(String gap) {
		this.gap = gap;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCi_sl_no() {
		return ci_sl_no;
	}

	public void setCi_sl_no(String ci_sl_no) {
		this.ci_sl_no = ci_sl_no;
	}

	public String getIndicator_desc() {
		return indicator_desc;
	}

	public void setIndicator_desc(String indicator_desc) {
		this.indicator_desc = indicator_desc;
	}

	@Override
	public String toString() {
		return "Form1BSaveIndicatorsModel [area_id=" + area_id + ", area_name=" + area_name + ", indicator_val="
				+ indicator_val + ", indicator_id=" + indicator_id + ", data=" + data + ", gap=" + gap + ", expected="
				+ expected + ", source=" + source + ", ci_sl_no=" + ci_sl_no + ", indicator_desc=" + indicator_desc
				+ "]";
	}
	
	
}
