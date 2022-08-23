package com.tattvafoundation.diphonline.model;

public class Form1BSaveDocumentsArray {
	
	private String document_details;
	private String document_sanctioned;
	private String document_available_functional;
	private String document_gap;
	private String dist_demogra_dtl_id;
	private String primary_key;
	
	public Form1BSaveDocumentsArray() {
		
	}

	public Form1BSaveDocumentsArray(String document_details, String document_sanctioned,
			String document_available_functional, String document_gap, String dist_demogra_dtl_id, String primary_key) {
		this.document_details = document_details;
		this.document_sanctioned = document_sanctioned;
		this.document_available_functional = document_available_functional;
		this.document_gap = document_gap;
		this.dist_demogra_dtl_id = dist_demogra_dtl_id;
		this.primary_key = primary_key;
	}

	public String getDocument_details() {
		return document_details;
	}

	public void setDocument_details(String document_details) {
		this.document_details = document_details;
	}

	public String getDocument_sanctioned() {
		return document_sanctioned;
	}

	public void setDocument_sanctioned(String document_sanctioned) {
		this.document_sanctioned = document_sanctioned;
	}

	public String getDocument_available_functional() {
		return document_available_functional;
	}

	public void setDocument_available_functional(String document_available_functional) {
		this.document_available_functional = document_available_functional;
	}

	public String getDocument_gap() {
		return document_gap;
	}

	public void setDocument_gap(String document_gap) {
		this.document_gap = document_gap;
	}

	public String getDist_demogra_dtl_id() {
		return dist_demogra_dtl_id;
	}

	public void setDist_demogra_dtl_id(String dist_demogra_dtl_id) {
		this.dist_demogra_dtl_id = dist_demogra_dtl_id;
	}

	public String getPrimary_key() {
		return primary_key;
	}

	public void setPrimary_key(String primary_key) {
		this.primary_key = primary_key;
	}

	@Override
	public String toString() {
		return "Form1BSaveDocumentsArray [document_details=" + document_details + ", document_sanctioned="
				+ document_sanctioned + ", document_available_functional=" + document_available_functional
				+ ", document_gap=" + document_gap + ", dist_demogra_dtl_id=" + dist_demogra_dtl_id + ", primary_key="
				+ primary_key + "]";
	}
	
	
}
