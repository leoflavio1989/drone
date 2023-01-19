package com.musala.drone.type;

public enum DroneModel {
	LIGHTWEIGHT("Lightweight"), 
	MIDDLEWEIGHT("Middleweight"), 
	CRUISERWEIGHT("Cruiserweight"),
	HEAVYWEIGHT("Heavyweight");

	private final String value;

	private DroneModel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
