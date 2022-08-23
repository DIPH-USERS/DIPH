package com.tattvafoundation.diphonline.model;

import java.util.List;

public class SendAndroidForm1BSynchedDataBean {

	private List<Form1BPrimaryTableDataBean> formB;
	private List<Form1BNgoTableDataBean> formBngo;
	private List<Form1BStakeHolderTableDataBean> formBstakeholders;
	private List<Form1BIndicatorsTableDataBean> formBcoverageIndicators;
	private List<Form1BdocumentsBean> formBinfra;
	private List<Form1BdocumentsBean> formBfinance;
	private List<Form1BdocumentsBean> formBsupplies;
	private List<Form1BdocumentsBean> formBtechnology;
	private List<Form1BdocumentsBean> formBhumanresource;	
	private String error_code;
	private String message;
	
	public SendAndroidForm1BSynchedDataBean() {
		
	}

	public SendAndroidForm1BSynchedDataBean(List<Form1BPrimaryTableDataBean> formB,
			List<Form1BNgoTableDataBean> formBngo, List<Form1BStakeHolderTableDataBean> formBstakeholders,
			List<Form1BIndicatorsTableDataBean> formBcoverageIndicators, List<Form1BdocumentsBean> formBinfra,
			List<Form1BdocumentsBean> formBfinance, List<Form1BdocumentsBean> formBsupplies,
			List<Form1BdocumentsBean> formBtechnology, List<Form1BdocumentsBean> formBhumanresource, String error_code,
			String message) {
		this.formB = formB;
		this.formBngo = formBngo;
		this.formBstakeholders = formBstakeholders;
		this.formBcoverageIndicators = formBcoverageIndicators;
		this.formBinfra = formBinfra;
		this.formBfinance = formBfinance;
		this.formBsupplies = formBsupplies;
		this.formBtechnology = formBtechnology;
		this.formBhumanresource = formBhumanresource;
		this.error_code = error_code;
		this.message = message;
	}

	public List<Form1BPrimaryTableDataBean> getFormB() {
		return formB;
	}

	public void setFormB(List<Form1BPrimaryTableDataBean> formB) {
		this.formB = formB;
	}

	public List<Form1BNgoTableDataBean> getFormBngo() {
		return formBngo;
	}

	public void setFormBngo(List<Form1BNgoTableDataBean> formBngo) {
		this.formBngo = formBngo;
	}

	public List<Form1BStakeHolderTableDataBean> getFormBstakeholders() {
		return formBstakeholders;
	}

	public void setFormBstakeholders(List<Form1BStakeHolderTableDataBean> formBstakeholders) {
		this.formBstakeholders = formBstakeholders;
	}

	public List<Form1BIndicatorsTableDataBean> getFormBcoverageIndicators() {
		return formBcoverageIndicators;
	}

	public void setFormBcoverageIndicators(List<Form1BIndicatorsTableDataBean> formBcoverageIndicators) {
		this.formBcoverageIndicators = formBcoverageIndicators;
	}

	public List<Form1BdocumentsBean> getFormBinfra() {
		return formBinfra;
	}

	public void setFormBinfra(List<Form1BdocumentsBean> formBinfra) {
		this.formBinfra = formBinfra;
	}

	public List<Form1BdocumentsBean> getFormBfinance() {
		return formBfinance;
	}

	public void setFormBfinance(List<Form1BdocumentsBean> formBfinance) {
		this.formBfinance = formBfinance;
	}

	public List<Form1BdocumentsBean> getFormBsupplies() {
		return formBsupplies;
	}

	public void setFormBsupplies(List<Form1BdocumentsBean> formBsupplies) {
		this.formBsupplies = formBsupplies;
	}

	public List<Form1BdocumentsBean> getFormBtechnology() {
		return formBtechnology;
	}

	public void setFormBtechnology(List<Form1BdocumentsBean> formBtechnology) {
		this.formBtechnology = formBtechnology;
	}

	public List<Form1BdocumentsBean> getFormBhumanresource() {
		return formBhumanresource;
	}

	public void setFormBhumanresource(List<Form1BdocumentsBean> formBhumanresource) {
		this.formBhumanresource = formBhumanresource;
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
		return "SendAndroidForm1BSynchedDataBean [formB=" + formB + ", formBngo=" + formBngo + ", formBstakeholders="
				+ formBstakeholders + ", formBcoverageIndicators=" + formBcoverageIndicators + ", formBinfra="
				+ formBinfra + ", formBfinance=" + formBfinance + ", formBsupplies=" + formBsupplies
				+ ", formBtechnology=" + formBtechnology + ", formBhumanresource=" + formBhumanresource
				+ ", error_code=" + error_code + ", message=" + message + "]";
	}

	
	
}
