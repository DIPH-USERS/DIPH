package com.tattvafoundation.diphonline.model;

import java.util.List;

public class AllUsersBean {

	private String user_nm;
	private String user_pass;
	private String user_status;
	private String emailId;
	private String can_create;
	private String can_view;
	private String can_edit;
	private String can_delete;
	private List<String> district;
	private List<String> cycle;
	private List<String> year;
	
	
	public AllUsersBean() {
		
	}


	public AllUsersBean(String user_nm, String user_pass, String user_status, String emailId, String can_create,
			String can_view, String can_edit, String can_delete, List<String> district, List<String> cycle,
			List<String> year) {		
		this.user_nm = user_nm;
		this.user_pass = user_pass;
		this.user_status = user_status;
		this.emailId = emailId;
		this.can_create = can_create;
		this.can_view = can_view;
		this.can_edit = can_edit;
		this.can_delete = can_delete;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
	}


	public String getUser_nm() {
		return user_nm;
	}


	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}


	public String getUser_pass() {
		return user_pass;
	}


	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}


	public String getUser_status() {
		return user_status;
	}


	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getCan_create() {
		return can_create;
	}


	public void setCan_create(String can_create) {
		this.can_create = can_create;
	}


	public String getCan_view() {
		return can_view;
	}


	public void setCan_view(String can_view) {
		this.can_view = can_view;
	}


	public String getCan_edit() {
		return can_edit;
	}


	public void setCan_edit(String can_edit) {
		this.can_edit = can_edit;
	}


	public String getCan_delete() {
		return can_delete;
	}


	public void setCan_delete(String can_delete) {
		this.can_delete = can_delete;
	}


	public List<String> getDistrict() {
		return district;
	}


	public void setDistrict(List<String> district) {
		this.district = district;
	}


	public List<String> getCycle() {
		return cycle;
	}


	public void setCycle(List<String> cycle) {
		this.cycle = cycle;
	}


	public List<String> getYear() {
		return year;
	}


	public void setYear(List<String> year) {
		this.year = year;
	}


	@Override
	public String toString() {
		return "AllUsersBean [user_nm=" + user_nm + ", user_pass=" + user_pass + ", user_status=" + user_status
				+ ", emailId=" + emailId + ", can_create=" + can_create + ", can_view=" + can_view + ", can_edit="
				+ can_edit + ", can_delete=" + can_delete + ", district=" + district + ", cycle=" + cycle + ", year="
				+ year + "]";
	}
}
