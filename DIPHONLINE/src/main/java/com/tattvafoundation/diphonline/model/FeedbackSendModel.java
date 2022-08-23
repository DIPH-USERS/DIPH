package com.tattvafoundation.diphonline.model;

public class FeedbackSendModel {
	String status;

	public FeedbackSendModel(String status) {
		
		this.status = status;
	}

	public FeedbackSendModel() {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FeedbackSendModel [status=" + status + "]";
	}
	
	
}
