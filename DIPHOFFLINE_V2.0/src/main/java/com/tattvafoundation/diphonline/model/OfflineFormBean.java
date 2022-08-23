package com.tattvafoundation.diphonline.model;

/**
 * <h1>Offline form bean!</h1> Java bean for offline JSON data , JSON data will
 * be derived from offline HTML form
 * <p>
 *
 * @author Mohd. Mohsin Ansari
 * @version 1.0
 * @since 2020-11-23
 */

public class OfflineFormBean {

	private Form1ASave form1A;
	private Form1BSave form1B;
	private Form2Engage form2Engage;
	private Form3Define form3Define;
	private Form4Plan form4Plan;
	private Form5Followup form5Followup;
	private Supplementaryform1A supplementaryform;
	private String district;
	private String cycle;
	private String year;
	private String userid;

	public OfflineFormBean() {
	}

	

	public OfflineFormBean(Form1ASave form1a, Form1BSave form1b, Form2Engage form2Engage, Form3Define form3Define,
			Form4Plan form4Plan, Form5Followup form5Followup, Supplementaryform1A supplementaryform, String district,
			String cycle, String year, String userid) {
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
	}



	public Form1ASave getForm1A() {
		return form1A;
	}

	public void setForm1A(Form1ASave form1a) {
		form1A = form1a;
	}

	public Form1BSave getForm1B() {
		return form1B;
	}

	public void setForm1B(Form1BSave form1b) {
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



	@Override
	public String toString() {
		return "OfflineFormBean [form1A=" + form1A + ", form1B=" + form1B + ", form2Engage=" + form2Engage
				+ ", form3Define=" + form3Define + ", form4Plan=" + form4Plan + ", form5Followup=" + form5Followup
				+ ", supplementaryform=" + supplementaryform + ", district=" + district + ", cycle=" + cycle + ", year="
				+ year + ", userid=" + userid + "]";
	}

	

}