package com.tattvafoundation.diphonline.model;


public class AllFormsDataFetchFromAndroidBean {

	private Form1AConsumeDataFromAndroidBean form1a;
	private Form1BConsumeDataFromAndroidBean form1b;
	private Form2EnagageSendAllDataBean form2;
	private Form3DefineSendAllDataBean form3;
	private Form4PlanSendAllDataBean form4;
	private Form5FollowUpSendAllDataBean form5;
	private FormSupplementarySendAllDataBean formsupplementary;
	
	public AllFormsDataFetchFromAndroidBean() {
		
	}

	public AllFormsDataFetchFromAndroidBean(Form1AConsumeDataFromAndroidBean form1a,
			Form1BConsumeDataFromAndroidBean form1b, Form2EnagageSendAllDataBean form2,
			Form3DefineSendAllDataBean form3, Form4PlanSendAllDataBean form4, Form5FollowUpSendAllDataBean form5,
			FormSupplementarySendAllDataBean formsupplementary) {
		this.form1a = form1a;
		this.form1b = form1b;
		this.form2 = form2;
		this.form3 = form3;
		this.form4 = form4;
		this.form5 = form5;
		this.formsupplementary = formsupplementary;
	}

	public Form1AConsumeDataFromAndroidBean getForm1a() {
		return form1a;
	}

	public void setForm1a(Form1AConsumeDataFromAndroidBean form1a) {
		this.form1a = form1a;
	}

	public Form1BConsumeDataFromAndroidBean getForm1b() {
		return form1b;
	}

	public void setForm1b(Form1BConsumeDataFromAndroidBean form1b) {
		this.form1b = form1b;
	}

	public Form2EnagageSendAllDataBean getForm2() {
		return form2;
	}

	public void setForm2(Form2EnagageSendAllDataBean form2) {
		this.form2 = form2;
	}

	public Form3DefineSendAllDataBean getForm3() {
		return form3;
	}

	public void setForm3(Form3DefineSendAllDataBean form3) {
		this.form3 = form3;
	}

	public Form4PlanSendAllDataBean getForm4() {
		return form4;
	}

	public void setForm4(Form4PlanSendAllDataBean form4) {
		this.form4 = form4;
	}

	public Form5FollowUpSendAllDataBean getForm5() {
		return form5;
	}

	public void setForm5(Form5FollowUpSendAllDataBean form5) {
		this.form5 = form5;
	}

	public FormSupplementarySendAllDataBean getFormsupplementary() {
		return formsupplementary;
	}

	public void setFormsupplementary(FormSupplementarySendAllDataBean formsupplementary) {
		this.formsupplementary = formsupplementary;
	}

	@Override
	public String toString() {
		return "AllFormsDataFetchFromAndroidBean [form1a=" + form1a + ", form1b=" + form1b + ", form2=" + form2
				+ ", form3=" + form3 + ", form4=" + form4 + ", form5=" + form5 + ", formsupplementary="
				+ formsupplementary + "]";
	}

	
	
}
