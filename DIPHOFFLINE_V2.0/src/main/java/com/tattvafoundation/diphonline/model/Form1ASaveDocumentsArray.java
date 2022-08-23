package com.tattvafoundation.diphonline.model;

public class Form1ASaveDocumentsArray {
	
	private String document_val;
	private String document_availability;
	private String document_source;
	private String doc_db_doc_id;
	
	public Form1ASaveDocumentsArray() {
		
	}

	public Form1ASaveDocumentsArray(String document_val, String document_availability, String document_source,
			String doc_db_doc_id) {
		this.document_val = document_val;
		this.document_availability = document_availability;
		this.document_source = document_source;
		this.doc_db_doc_id = doc_db_doc_id;
	}

	public String getDocument_val() {
		return document_val;
	}

	public void setDocument_val(String document_val) {
		this.document_val = document_val;
	}

	public String getDocument_availability() {
		return document_availability;
	}

	public void setDocument_availability(String document_availability) {
		this.document_availability = document_availability;
	}

	public String getDocument_source() {
		return document_source;
	}

	public void setDocument_source(String document_source) {
		this.document_source = document_source;
	}

	public String getDoc_db_doc_id() {
		return doc_db_doc_id;
	}

	public void setDoc_db_doc_id(String doc_db_doc_id) {
		this.doc_db_doc_id = doc_db_doc_id;
	}

	@Override
	public String toString() {
		return "Form1ASaveDocumentsArray [document_val=" + document_val + ", document_availability="
				+ document_availability + ", document_source=" + document_source + ", doc_db_doc_id=" + doc_db_doc_id
				+ "]";
	}
	
	
}
