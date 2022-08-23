package com.tattvafoundation.diphonline.model;

public class Form1ADocumentsAvailable {

	private String doc_level;
	private String doc_document;
	private String doc_source;
	
	
	public Form1ADocumentsAvailable(){
		
	}


	public Form1ADocumentsAvailable(String doc_level, String doc_document, String doc_source) {
		this.doc_level = doc_level;
		this.doc_document = doc_document;
		this.doc_source = doc_source;
	} 


	public String getDoc_level() {
		return doc_level;
	}


	public void setDoc_level(String doc_level) {
		this.doc_level = doc_level;
	}


	public String getDoc_document() {
		return doc_document;
	}


	public void setDoc_document(String doc_document) {
		this.doc_document = doc_document;
	}


	public String getDoc_source() {
		return doc_source;
	}


	public void setDoc_source(String doc_source) {
		this.doc_source = doc_source;
	}


	
	
	
}
