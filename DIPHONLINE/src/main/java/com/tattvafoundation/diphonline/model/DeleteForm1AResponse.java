package com.tattvafoundation.diphonline.model;

public class DeleteForm1AResponse {

	
	String processname;
	String response_val;
	
	public DeleteForm1AResponse(){
		
	}

	public DeleteForm1AResponse(String processname, String response_val) {
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
		return "DeleteForm1AResponse [processname=" + processname + ", response_val=" + response_val + "]";
	}
	
	
}
