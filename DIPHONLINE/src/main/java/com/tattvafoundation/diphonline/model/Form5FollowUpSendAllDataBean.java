package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form5FollowUpSendAllDataBean {

	private List<Form5FollowUpPrimaryTableDataBean> form5followup;
	private List<Form5PartAStakesMeetingDataBean> form5partamajorholder;
	private List<Form5PartADIPHCycleIndicatorComparison> form5partaindicator;
	private List<Form5PartBActionPlanIndicatorFollowUpStatus>  form5partb;
	private List<Form5PartBIndicatorProgress> form5partbindiprogress; 
	private String error_code;
	private String message;
	
	
	
	public Form5FollowUpSendAllDataBean() {		
	}



	public Form5FollowUpSendAllDataBean(List<Form5FollowUpPrimaryTableDataBean> form5followup,
			List<Form5PartAStakesMeetingDataBean> form5partamajorholder,
			List<Form5PartADIPHCycleIndicatorComparison> form5partaindicator,
			List<Form5PartBActionPlanIndicatorFollowUpStatus> form5partb,
			List<Form5PartBIndicatorProgress> form5partbindiprogress, String error_code, String message) {		
		this.form5followup = form5followup;
		this.form5partamajorholder = form5partamajorholder;
		this.form5partaindicator = form5partaindicator;
		this.form5partb = form5partb;
		this.form5partbindiprogress = form5partbindiprogress;
		this.error_code = error_code;
		this.message = message;
	}



	public List<Form5FollowUpPrimaryTableDataBean> getForm5followup() {
		return form5followup;
	}



	public void setForm5followup(List<Form5FollowUpPrimaryTableDataBean> form5followup) {
		this.form5followup = form5followup;
	}



	public List<Form5PartAStakesMeetingDataBean> getForm5partamajorholder() {
		return form5partamajorholder;
	}



	public void setForm5partamajorholder(List<Form5PartAStakesMeetingDataBean> form5partamajorholder) {
		this.form5partamajorholder = form5partamajorholder;
	}



	public List<Form5PartADIPHCycleIndicatorComparison> getForm5partaindicator() {
		return form5partaindicator;
	}



	public void setForm5partaindicator(List<Form5PartADIPHCycleIndicatorComparison> form5partaindicator) {
		this.form5partaindicator = form5partaindicator;
	}



	public List<Form5PartBActionPlanIndicatorFollowUpStatus> getForm5partb() {
		return form5partb;
	}



	public void setForm5partb(List<Form5PartBActionPlanIndicatorFollowUpStatus> form5partb) {
		this.form5partb = form5partb;
	}



	public List<Form5PartBIndicatorProgress> getForm5partbindiprogress() {
		return form5partbindiprogress;
	}



	public void setForm5partbindiprogress(List<Form5PartBIndicatorProgress> form5partbindiprogress) {
		this.form5partbindiprogress = form5partbindiprogress;
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
		return "Form5FollowUpSendAllDataBean [form5followup=" + form5followup + ", form5partamajorholder="
				+ form5partamajorholder + ", form5partaindicator=" + form5partaindicator + ", form5partb=" + form5partb
				+ ", form5partbindiprogress=" + form5partbindiprogress + ", error_code=" + error_code + ", message="
				+ message + "]";
	}

	
	
}
