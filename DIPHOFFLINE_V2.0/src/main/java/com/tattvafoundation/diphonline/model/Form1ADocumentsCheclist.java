package com.tattvafoundation.diphonline.model;

public class Form1ADocumentsCheclist {

	private long doc_db_check_id;
	private String date_of_verification;
	private String filled_by;
	private int verified_by;
	private String verified_by_other;
	private String completed;
	
	public Form1ADocumentsCheclist(){
		
	}

	public Form1ADocumentsCheclist(long doc_db_check_id, String date_of_verification, String filled_by, int verified_by,
			String verified_by_other, String completed) {
		this.doc_db_check_id = doc_db_check_id;
		this.date_of_verification = date_of_verification;
		this.filled_by = filled_by;
		this.verified_by = verified_by;
		this.verified_by_other = verified_by_other;
		this.completed = completed;
	}

	public long getDoc_db_check_id() {
		return doc_db_check_id;
	}

	public void setDoc_db_check_id(long doc_db_check_id) {
		this.doc_db_check_id = doc_db_check_id;
	}

	public String getDate_of_verification() {
		return date_of_verification;
	}

	public void setDate_of_verification(String date_of_verification) {
		this.date_of_verification = date_of_verification;
	}

	public String getFilled_by() {
		return filled_by;
	}

	public void setFilled_by(String filled_by) {
		this.filled_by = filled_by;
	}

	public int getVerified_by() {
		return verified_by;
	}

	public void setVerified_by(int verified_by) {
		this.verified_by = verified_by;
	}

	public String getVerified_by_other() {
		return verified_by_other;
	}

	public void setVerified_by_other(String verified_by_other) {
		this.verified_by_other = verified_by_other;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Form1ADocumentsCheclist [doc_db_check_id=" + doc_db_check_id + ", date_of_verification="
				+ date_of_verification + ", filled_by=" + filled_by + ", verified_by=" + verified_by
				+ ", verified_by_other=" + verified_by_other + ", completed=" + completed + "]";
	}

	
	
	
}
