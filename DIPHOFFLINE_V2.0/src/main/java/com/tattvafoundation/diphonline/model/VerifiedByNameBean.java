package com.tattvafoundation.diphonline.model;

public class VerifiedByNameBean {

	private String id;
	private String name;
	
	public VerifiedByNameBean() {
		
	}
	
	public VerifiedByNameBean(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "VerifiedByNameBean [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
