package com.tattvafoundation.diphonline.model;

public class Supplementaryform1ACommonArray {

	private String document_name;
	private String data_action_point_id;
	private String insert="0";
	
	public Supplementaryform1ACommonArray() {
		
	}

	public Supplementaryform1ACommonArray(String document_name, String data_action_point_id, String insert) {
		this.document_name = document_name;
		this.data_action_point_id = data_action_point_id;
		this.insert = insert;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public String getData_action_point_id() {
		return data_action_point_id;
	}

	public void setData_action_point_id(String data_action_point_id) {
		this.data_action_point_id = data_action_point_id;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	@Override
	public String toString() {
		return "Supplementaryform1ACommonArray [document_name=" + document_name + ", data_action_point_id="
				+ data_action_point_id + ", insert=" + insert + "]";
	}

	
	
}
