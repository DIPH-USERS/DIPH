package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form1BConsumeDataFromAndroidBean {

	private List<Form1BPrimaryTableDataBean> formB;
	private List<Form1BNgoTableDataBean> formBngo;
	private List<Form1BStakeHolderTableDataBean> formBstakeholders;
	private List<Form1BIndicatorsTableDataBean> formBcoverageIndicators;
	private List<Form1BdocumentsBean> formBinfra;
	private List<Form1BdocumentsBean> formBfinance;
	private List<Form1BdocumentsBean> formBsupplies;
	private List<Form1BdocumentsBean> formBtechnology;
	private List<Form1BdocumentsBean> formBhumanresource;
	
	
	public Form1BConsumeDataFromAndroidBean() {
		
	}
	
	
	public Form1BConsumeDataFromAndroidBean(List<Form1BPrimaryTableDataBean> formB,
			List<Form1BNgoTableDataBean> formBngo, List<Form1BStakeHolderTableDataBean> formBstakeholders,
			List<Form1BIndicatorsTableDataBean> formBcoverageIndicators, List<Form1BdocumentsBean> formBinfra,
			List<Form1BdocumentsBean> formBfinance, List<Form1BdocumentsBean> formBsupplies,
			List<Form1BdocumentsBean> formBtechnology, List<Form1BdocumentsBean> formBhumanresource) {
		this.formB = formB;
		this.formBngo = formBngo;
		this.formBstakeholders = formBstakeholders;
		this.formBcoverageIndicators = formBcoverageIndicators;
		this.formBinfra = formBinfra;
		this.formBfinance = formBfinance;
		this.formBsupplies = formBsupplies;
		this.formBtechnology = formBtechnology;
		this.formBhumanresource = formBhumanresource;
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


	@Override
	public String toString() {
		return "Form1BConsumeDataFromAndroidBean [formB=" + formB + ", formBngo=" + formBngo + ", formBstakeholders="
				+ formBstakeholders + ", formBcoverageIndicators=" + formBcoverageIndicators + ", formBinfra="
				+ formBinfra + ", formBfinance=" + formBfinance + ", formBsupplies=" + formBsupplies
				+ ", formBtechnology=" + formBtechnology + ", formBhumanresource=" + formBhumanresource + "]";
	}
	
	
	
	
}
