package com.tattvafoundation.diphonline.indicator.entity;

public class User {
	
	private String user_id;
	private String user_nm;
	private String user_pass;
	private String user_status;
	private String district_id;
	private String can_create;
	private String can_view;
	private String can_edit;
	private String can_delete;
	private String emailId;
	
	public User() {
		super();
	}

	public User(String user_id, String user_nm, String user_pass, String user_status, String district_id,
			String can_create, String can_view, String can_edit, String can_delete, String emailId) {
		super();
		this.user_id = user_id;
		this.user_nm = user_nm;
		this.user_pass = user_pass;
		this.user_status = user_status;
		this.district_id = district_id;
		this.can_create = can_create;
		this.can_view = can_view;
		this.can_edit = can_edit;
		this.can_delete = can_delete;
		this.emailId = emailId;
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

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((can_create == null) ? 0 : can_create.hashCode());
		result = prime * result + ((can_delete == null) ? 0 : can_delete.hashCode());
		result = prime * result + ((can_edit == null) ? 0 : can_edit.hashCode());
		result = prime * result + ((can_view == null) ? 0 : can_view.hashCode());
		result = prime * result + ((district_id == null) ? 0 : district_id.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		result = prime * result + ((user_nm == null) ? 0 : user_nm.hashCode());
		result = prime * result + ((user_pass == null) ? 0 : user_pass.hashCode());
		result = prime * result + ((user_status == null) ? 0 : user_status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (can_create == null) {
			if (other.can_create != null)
				return false;
		} else if (!can_create.equals(other.can_create))
			return false;
		if (can_delete == null) {
			if (other.can_delete != null)
				return false;
		} else if (!can_delete.equals(other.can_delete))
			return false;
		if (can_edit == null) {
			if (other.can_edit != null)
				return false;
		} else if (!can_edit.equals(other.can_edit))
			return false;
		if (can_view == null) {
			if (other.can_view != null)
				return false;
		} else if (!can_view.equals(other.can_view))
			return false;
		if (district_id == null) {
			if (other.district_id != null)
				return false;
		} else if (!district_id.equals(other.district_id))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		if (user_nm == null) {
			if (other.user_nm != null)
				return false;
		} else if (!user_nm.equals(other.user_nm))
			return false;
		if (user_pass == null) {
			if (other.user_pass != null)
				return false;
		} else if (!user_pass.equals(other.user_pass))
			return false;
		if (user_status == null) {
			if (other.user_status != null)
				return false;
		} else if (!user_status.equals(other.user_status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_nm=" + user_nm + ", user_pass=" + user_pass + ", user_status="
				+ user_status + ", district_id=" + district_id + ", can_create=" + can_create + ", can_view=" + can_view
				+ ", can_edit=" + can_edit + ", can_delete=" + can_delete + ", emailId=" + emailId + "]";
	}
	
	

}
