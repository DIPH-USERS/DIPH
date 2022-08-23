package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form4PlanSendAllDataBean {

	private List<Form4PlanPrimaryTableDataBean> form4Plan;
	private List<Form4ActionPlanActionPointsBean> form4ActionPlanActionPoints;
	private List<Form4ActionPlanIndicatorsBean> form4ActionPlanIndicators;
	private String error_code;
	private String message;

	public Form4PlanSendAllDataBean() {

	}

	public Form4PlanSendAllDataBean(List<Form4PlanPrimaryTableDataBean> form4Plan,
			List<Form4ActionPlanActionPointsBean> form4ActionPlanActionPoints,
			List<Form4ActionPlanIndicatorsBean> form4ActionPlanIndicators, String error_code, String message) {
		
		this.form4Plan = form4Plan;
		this.form4ActionPlanActionPoints = form4ActionPlanActionPoints;
		this.form4ActionPlanIndicators = form4ActionPlanIndicators;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Form4PlanPrimaryTableDataBean> getForm4Plan() {
		return form4Plan;
	}

	public void setForm4Plan(List<Form4PlanPrimaryTableDataBean> form4Plan) {
		this.form4Plan = form4Plan;
	}

	public List<Form4ActionPlanActionPointsBean> getForm4ActionPlanActionPoints() {
		return form4ActionPlanActionPoints;
	}

	public void setForm4ActionPlanActionPoints(List<Form4ActionPlanActionPointsBean> form4ActionPlanActionPoints) {
		this.form4ActionPlanActionPoints = form4ActionPlanActionPoints;
	}

	public List<Form4ActionPlanIndicatorsBean> getForm4ActionPlanIndicators() {
		return form4ActionPlanIndicators;
	}

	public void setForm4ActionPlanIndicators(List<Form4ActionPlanIndicatorsBean> form4ActionPlanIndicators) {
		this.form4ActionPlanIndicators = form4ActionPlanIndicators;
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
		return "Form4PlanSendAllDataBean [form4Plan=" + form4Plan + ", form4ActionPlanActionPoints="
				+ form4ActionPlanActionPoints + ", form4ActionPlanIndicators=" + form4ActionPlanIndicators
				+ ", error_code=" + error_code + ", message=" + message + "]";
	}

	
	
}
