package com.tattvafoundation.diphonline.model;

public class Cycle {

	//cycle_id,cycle_name
	private int cycle_id;
	private String cycle_name;
	
	public Cycle() {
		
	}

	public Cycle(int cycle_id, String cycle_name) {
		this.cycle_id = cycle_id;
		this.cycle_name = cycle_name;
	}

	public int getCycle_id() {
		return cycle_id;
	}

	public void setCycle_id(int cycle_id) {
		this.cycle_id = cycle_id;
	}

	public String getCycle_name() {
		return cycle_name;
	}

	public void setCycle_name(String cycle_name) {
		this.cycle_name = cycle_name;
	}

	@Override
	public String toString() {
		return "Cycle [cycle_id=" + cycle_id + ", cycle_name=" + cycle_name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cycle_id;
		result = prime * result + ((cycle_name == null) ? 0 : cycle_name.hashCode());
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
		Cycle other = (Cycle) obj;
		if (cycle_id != other.cycle_id)
			return false;
		if (cycle_name == null) {
			if (other.cycle_name != null)
				return false;
		} else if (!cycle_name.equals(other.cycle_name))
			return false;
		return true;
	}
	
	
	
}
