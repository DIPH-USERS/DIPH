package com.tattvafoundation.diphonline.model;

import java.util.List;

import com.tattvafoundation.diphonline.controller.OptionalindicatorBean;

public class SendAndroidAllFormsSynchedDataBean {

	private List<Form1ASendAllDataToAndroidBean> form1a;
	private List<SendAndroidForm1BSynchedDataBean> form1b;
	private List<Form2EnagageSendAllDataBean> form2;
	private List<Form3DefineSendAllDataBean> form3;
	private List<Form4PlanSendAllDataBean> form4;
	private List<Form5FollowUpSendAllDataBean> form5;
	private List<FormSupplementarySendAllDataBean> formsupplementary; 
	private List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list;
	private List<OptionalindicatorBean> optional_indicators_list;
	private String error_code;
	private String message;
	
	public SendAndroidAllFormsSynchedDataBean() {
		
	}

	public SendAndroidAllFormsSynchedDataBean(List<Form1ASendAllDataToAndroidBean> form1a,
			List<SendAndroidForm1BSynchedDataBean> form1b, List<Form2EnagageSendAllDataBean> form2,
			List<Form3DefineSendAllDataBean> form3, List<Form4PlanSendAllDataBean> form4,
			List<Form5FollowUpSendAllDataBean> form5, List<FormSupplementarySendAllDataBean> formsupplementary,
			List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list,
			List<OptionalindicatorBean> optional_indicators_list, String error_code, String message) {
		this.form1a = form1a;
		this.form1b = form1b;
		this.form2 = form2;
		this.form3 = form3;
		this.form4 = form4;
		this.form5 = form5;
		this.formsupplementary = formsupplementary;
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
		this.optional_indicators_list = optional_indicators_list;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Form1ASendAllDataToAndroidBean> getForm1a() {
		return form1a;
	}

	public void setForm1a(List<Form1ASendAllDataToAndroidBean> form1a) {
		this.form1a = form1a;
	}

	public List<SendAndroidForm1BSynchedDataBean> getForm1b() {
		return form1b;
	}

	public void setForm1b(List<SendAndroidForm1BSynchedDataBean> form1b) {
		this.form1b = form1b;
	}

	public List<Form2EnagageSendAllDataBean> getForm2() {
		return form2;
	}

	public void setForm2(List<Form2EnagageSendAllDataBean> form2) {
		this.form2 = form2;
	}

	public List<Form3DefineSendAllDataBean> getForm3() {
		return form3;
	}

	public void setForm3(List<Form3DefineSendAllDataBean> form3) {
		this.form3 = form3;
	}

	public List<Form4PlanSendAllDataBean> getForm4() {
		return form4;
	}

	public void setForm4(List<Form4PlanSendAllDataBean> form4) {
		this.form4 = form4;
	}

	public List<Form5FollowUpSendAllDataBean> getForm5() {
		return form5;
	}

	public void setForm5(List<Form5FollowUpSendAllDataBean> form5) {
		this.form5 = form5;
	}

	public List<FormSupplementarySendAllDataBean> getFormsupplementary() {
		return formsupplementary;
	}

	public void setFormsupplementary(List<FormSupplementarySendAllDataBean> formsupplementary) {
		this.formsupplementary = formsupplementary;
	}

	public List<Menu_Area_Indicator_Object> getAreas_Id_Indicators_map_list() {
		return areas_Id_Indicators_map_list;
	}

	public void setAreas_Id_Indicators_map_list(List<Menu_Area_Indicator_Object> areas_Id_Indicators_map_list) {
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
	}

	public List<OptionalindicatorBean> getOptional_indicators_list() {
		return optional_indicators_list;
	}

	public void setOptional_indicators_list(List<OptionalindicatorBean> optional_indicators_list) {
		this.optional_indicators_list = optional_indicators_list;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SendAndroidAllFormsSynchedDataBean [form1a=" + form1a + ", form1b=" + form1b + ", form2=" + form2
				+ ", form3=" + form3 + ", form4=" + form4 + ", form5=" + form5 + ", formsupplementary="
				+ formsupplementary + ", areas_Id_Indicators_map_list=" + areas_Id_Indicators_map_list
				+ ", optional_indicators_list=" + optional_indicators_list + ", error_code=" + error_code + ", message="
				+ message + "]";
	}

	
}
