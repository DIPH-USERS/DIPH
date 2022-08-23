package com.tattvafoundation.diphonline.model;

import java.util.List;

import com.tattvafoundation.diphonline.indicator.entity.Area;
import com.tattvafoundation.diphonline.indicator.entity.AreaIndicatorMapping;
import com.tattvafoundation.diphonline.indicator.entity.Indicator;
import com.tattvafoundation.diphonline.indicator.entity.OptionalIndicator;

public class OfflineFormBeanVersion2 {
	
	private Form1AView form1A;
	private Form1BView form1B;
	private Form2Engage form2Engage;
	private Form3Define form3Define;
	private Form4Plan form4Plan;
	private Form5Followup form5Followup;
	private Supplementaryform1A supplementaryform;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	
	private List<Area> areaList;
	private List<AreaIndicatorMapping> areaIndicatorMappingList;
	private List<Indicator> indicatorList;
	private List<OptionalIndicator> optionalIndicatorList;	
	
	public OfflineFormBeanVersion2() {
		super();
	}

	public OfflineFormBeanVersion2(Form1AView form1a, Form1BView form1b, Form2Engage form2Engage,
			Form3Define form3Define, Form4Plan form4Plan, Form5Followup form5Followup,
			Supplementaryform1A supplementaryform, String district, String cycle, String year, String userid,
			List<Area> areaList, List<AreaIndicatorMapping> areaIndicatorMappingList, List<Indicator> indicatorList,
			List<OptionalIndicator> optionalIndicatorList) {
		super();
		this.form1A = form1a;
		this.form1B = form1b;
		this.form2Engage = form2Engage;
		this.form3Define = form3Define;
		this.form4Plan = form4Plan;
		this.form5Followup = form5Followup;
		this.supplementaryform = supplementaryform;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.areaList = areaList;
		this.areaIndicatorMappingList = areaIndicatorMappingList;
		this.indicatorList = indicatorList;
		this.optionalIndicatorList = optionalIndicatorList;
	}

	public Form1AView getForm1A() {
		return form1A;
	}

	public void setForm1A(Form1AView form1a) {
		form1A = form1a;
	}

	public Form1BView getForm1B() {
		return form1B;
	}

	public void setForm1B(Form1BView form1b) {
		form1B = form1b;
	}

	public Form2Engage getForm2Engage() {
		return form2Engage;
	}

	public void setForm2Engage(Form2Engage form2Engage) {
		this.form2Engage = form2Engage;
	}

	public Form3Define getForm3Define() {
		return form3Define;
	}

	public void setForm3Define(Form3Define form3Define) {
		this.form3Define = form3Define;
	}

	public Form4Plan getForm4Plan() {
		return form4Plan;
	}

	public void setForm4Plan(Form4Plan form4Plan) {
		this.form4Plan = form4Plan;
	}

	public Form5Followup getForm5Followup() {
		return form5Followup;
	}

	public void setForm5Followup(Form5Followup form5Followup) {
		this.form5Followup = form5Followup;
	}

	public Supplementaryform1A getSupplementaryform() {
		return supplementaryform;
	}

	public void setSupplementaryform(Supplementaryform1A supplementaryform) {
		this.supplementaryform = supplementaryform;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public List<AreaIndicatorMapping> getAreaIndicatorMappingList() {
		return areaIndicatorMappingList;
	}

	public void setAreaIndicatorMappingList(List<AreaIndicatorMapping> areaIndicatorMappingList) {
		this.areaIndicatorMappingList = areaIndicatorMappingList;
	}

	public List<Indicator> getIndicatorList() {
		return indicatorList;
	}

	public void setIndicatorList(List<Indicator> indicatorList) {
		this.indicatorList = indicatorList;
	}

	public List<OptionalIndicator> getOptionalIndicatorList() {
		return optionalIndicatorList;
	}

	public void setOptionalIndicatorList(List<OptionalIndicator> optionalIndicatorList) {
		this.optionalIndicatorList = optionalIndicatorList;
	}

	@Override
	public String toString() {
		return "OfflineFormBeanVersion2 [form1A=" + form1A + ", form1B=" + form1B + ", form2Engage=" + form2Engage
				+ ", form3Define=" + form3Define + ", form4Plan=" + form4Plan + ", form5Followup=" + form5Followup
				+ ", supplementaryform=" + supplementaryform + ", district=" + district + ", cycle=" + cycle + ", year="
				+ year + ", userid=" + userid + ", areaList=" + areaList + ", areaIndicatorMappingList="
				+ areaIndicatorMappingList + ", indicatorList=" + indicatorList + ", optionalIndicatorList="
				+ optionalIndicatorList + "]";
	}	
	
}
