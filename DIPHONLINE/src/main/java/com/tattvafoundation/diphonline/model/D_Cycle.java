package com.tattvafoundation.diphonline.model;

import java.util.ArrayList;
import java.util.List;

public class D_Cycle {

	private List<Cycle> cyclesList;

	public D_Cycle() {

	}

	public D_Cycle(List<Cycle> cyclesList) {
		this.cyclesList = cyclesList;
	}

	public List<Cycle> getCyclesList() {

		if (cyclesList == null) {
			cyclesList = new ArrayList<>();
		}

		return cyclesList;
	}

	public void setCyclesList(List<Cycle> cyclesList) {
		this.cyclesList = cyclesList;
	}

}
