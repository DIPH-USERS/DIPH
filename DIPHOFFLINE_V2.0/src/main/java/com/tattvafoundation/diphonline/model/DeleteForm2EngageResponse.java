package com.tattvafoundation.diphonline.model;

public class DeleteForm2EngageResponse {
	
	String processname;
	String response_val;
	
	public DeleteForm2EngageResponse(){
		
	}

	public DeleteForm2EngageResponse(String processname, String response_val) {
		this.processname = processname;
		this.response_val = response_val;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getResponse_val() {
		return response_val;
	}

	public void setResponse_val(String response_val) {
		this.response_val = response_val;
	}

	@Override
	public String toString() {
		return "DeleteForm2EngageResponse [processname=" + processname + ", response_val=" + response_val + "]";
	}
	
	
	
}
