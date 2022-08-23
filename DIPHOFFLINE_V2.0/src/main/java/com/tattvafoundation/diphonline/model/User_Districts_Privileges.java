package com.tattvafoundation.diphonline.model;

public class User_Districts_Privileges {

	private String user_id;
	private String user_nm;
	private String user_info_id;
	private String all_districts;
	private String all_cycles;
	private String all_years;
	
	public User_Districts_Privileges() {
	}
	
	public User_Districts_Privileges(String user_id, String user_nm, String user_info_id, String all_districts,
			String all_cycles, String all_years) {
		this.user_id = user_id;
		this.user_nm = user_nm;
		this.user_info_id = user_info_id;
		this.all_districts = all_districts;
		this.all_cycles = all_cycles;
		this.all_years = all_years;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getUser_info_id() {
		return user_info_id;
	}

	public void setUser_info_id(String user_info_id) {
		this.user_info_id = user_info_id;
	}

	public String getAll_districts() {
		return all_districts;
	}

	public void setAll_districts(String all_districts) {
		this.all_districts = all_districts;
	}

	public String getAll_cycles() {
		return all_cycles;
	}

	public void setAll_cycles(String all_cycles) {
		this.all_cycles = all_cycles;
	}

	public String getAll_years() {
		return all_years;
	}

	public void setAll_years(String all_years) {
		this.all_years = all_years;
	}

	@Override
	public String toString() {
		return "User_Districts_Privileges [user_id=" + user_id + ", user_nm=" + user_nm + ", user_info_id="
				+ user_info_id + ", all_districts=" + all_districts + ", all_cycles=" + all_cycles + ", all_years="
				+ all_years + "]";
	}
	
	
	
}
