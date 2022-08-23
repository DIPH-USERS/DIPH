package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Supplementaryform1A {

	private String parta_document_title;
	private String parta_date_of_release;
	private String parta_primary_theme;
	private String parta_goal;
	private String parta_part;
	private String parta_da_action_point;
	private String partb_document_title;
	private String partb_date_of_release;
	private String partb_primary_theme;
	private String partb_goal;
	private String partb_part;
	private String partb_da_action_point;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private List<Supplementaryform1ACommonArray> parta_da_action_point_array;
	private List<Supplementaryform1ACommonArray> partb_da_action_point_array;
	private String completed;
	private String source;
	
	public Supplementaryform1A() {
		
	}

	public Supplementaryform1A(String parta_document_title, String parta_date_of_release, String parta_primary_theme,
			String parta_goal, String parta_part, String parta_da_action_point, String partb_document_title,
			String partb_date_of_release, String partb_primary_theme, String partb_goal, String partb_part,
			String partb_da_action_point, String district, String cycle, String year, String userid,
			List<Supplementaryform1ACommonArray> parta_da_action_point_array,
			List<Supplementaryform1ACommonArray> partb_da_action_point_array, String completed, String source) {
		this.parta_document_title = parta_document_title;
		this.parta_date_of_release = parta_date_of_release;
		this.parta_primary_theme = parta_primary_theme;
		this.parta_goal = parta_goal;
		this.parta_part = parta_part;
		this.parta_da_action_point = parta_da_action_point;
		this.partb_document_title = partb_document_title;
		this.partb_date_of_release = partb_date_of_release;
		this.partb_primary_theme = partb_primary_theme;
		this.partb_goal = partb_goal;
		this.partb_part = partb_part;
		this.partb_da_action_point = partb_da_action_point;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.parta_da_action_point_array = parta_da_action_point_array;
		this.partb_da_action_point_array = partb_da_action_point_array;
		this.completed = completed;
		this.source = source;
	}

	public String getParta_document_title() {
		return parta_document_title;
	}

	public void setParta_document_title(String parta_document_title) {
		this.parta_document_title = parta_document_title;
	}

	public String getParta_date_of_release() {
		return parta_date_of_release;
	}

	public void setParta_date_of_release(String parta_date_of_release) {
		this.parta_date_of_release = parta_date_of_release;
	}

	public String getParta_primary_theme() {
		return parta_primary_theme;
	}

	public void setParta_primary_theme(String parta_primary_theme) {
		this.parta_primary_theme = parta_primary_theme;
	}

	public String getParta_goal() {
		return parta_goal;
	}

	public void setParta_goal(String parta_goal) {
		this.parta_goal = parta_goal;
	}

	public String getParta_part() {
		return parta_part;
	}

	public void setParta_part(String parta_part) {
		this.parta_part = parta_part;
	}

	public String getParta_da_action_point() {
		return parta_da_action_point;
	}

	public void setParta_da_action_point(String parta_da_action_point) {
		this.parta_da_action_point = parta_da_action_point;
	}

	public String getPartb_document_title() {
		return partb_document_title;
	}

	public void setPartb_document_title(String partb_document_title) {
		this.partb_document_title = partb_document_title;
	}

	public String getPartb_date_of_release() {
		return partb_date_of_release;
	}

	public void setPartb_date_of_release(String partb_date_of_release) {
		this.partb_date_of_release = partb_date_of_release;
	}

	public String getPartb_primary_theme() {
		return partb_primary_theme;
	}

	public void setPartb_primary_theme(String partb_primary_theme) {
		this.partb_primary_theme = partb_primary_theme;
	}

	public String getPartb_goal() {
		return partb_goal;
	}

	public void setPartb_goal(String partb_goal) {
		this.partb_goal = partb_goal;
	}

	public String getPartb_part() {
		return partb_part;
	}

	public void setPartb_part(String partb_part) {
		this.partb_part = partb_part;
	}

	public String getPartb_da_action_point() {
		return partb_da_action_point;
	}

	public void setPartb_da_action_point(String partb_da_action_point) {
		this.partb_da_action_point = partb_da_action_point;
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

	public List<Supplementaryform1ACommonArray> getParta_da_action_point_array() {
		return parta_da_action_point_array;
	}

	public void setParta_da_action_point_array(List<Supplementaryform1ACommonArray> parta_da_action_point_array) {
		this.parta_da_action_point_array = parta_da_action_point_array;
	}

	public List<Supplementaryform1ACommonArray> getPartb_da_action_point_array() {
		return partb_da_action_point_array;
	}

	public void setPartb_da_action_point_array(List<Supplementaryform1ACommonArray> partb_da_action_point_array) {
		this.partb_da_action_point_array = partb_da_action_point_array;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Supplementaryform1A [parta_document_title=" + parta_document_title + ", parta_date_of_release="
				+ parta_date_of_release + ", parta_primary_theme=" + parta_primary_theme + ", parta_goal=" + parta_goal
				+ ", parta_part=" + parta_part + ", parta_da_action_point=" + parta_da_action_point
				+ ", partb_document_title=" + partb_document_title + ", partb_date_of_release=" + partb_date_of_release
				+ ", partb_primary_theme=" + partb_primary_theme + ", partb_goal=" + partb_goal + ", partb_part="
				+ partb_part + ", partb_da_action_point=" + partb_da_action_point + ", district=" + district
				+ ", cycle=" + cycle + ", year=" + year + ", userid=" + userid + ", parta_da_action_point_array="
				+ parta_da_action_point_array + ", partb_da_action_point_array=" + partb_da_action_point_array
				+ ", completed=" + completed + ", source=" + source + "]";
	}
	
	

}