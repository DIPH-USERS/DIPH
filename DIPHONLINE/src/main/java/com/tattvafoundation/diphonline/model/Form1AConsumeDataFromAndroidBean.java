package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form1AConsumeDataFromAndroidBean {
	
	List<Form1APrimaryTableDataBean> formA;
	List<Form1ADocumentsTableDataBean> formAdocument;
	
	
	public Form1AConsumeDataFromAndroidBean() {
		
	}
	
	
	public Form1AConsumeDataFromAndroidBean(List<Form1APrimaryTableDataBean> formA,
			List<Form1ADocumentsTableDataBean> formAdocument) {
		this.formA = formA;
		this.formAdocument = formAdocument;
	}


	public List<Form1APrimaryTableDataBean> getFormA() {
		return formA;
	}


	public void setFormA(List<Form1APrimaryTableDataBean> formA) {
		this.formA = formA;
	}


	public List<Form1ADocumentsTableDataBean> getFormAdocument() {
		return formAdocument;
	}


	public void setFormAdocument(List<Form1ADocumentsTableDataBean> formAdocument) {
		this.formAdocument = formAdocument;
	}


	@Override
	public String toString() {
		return "Form1AConsumeDataFromAndroidBean [formA=" + formA + ", formAdocument=" + formAdocument + "]";
	}
}
