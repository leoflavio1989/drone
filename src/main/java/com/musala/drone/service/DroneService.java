package com.musala.drone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDto;
import com.musala.drone.model.Medication;
import com.musala.drone.type.DroneState;
import com.musala.drone.util.Validator;

@Service
public class DroneService {

	public DroneService() {

	}

	public String validateDroneData(DroneDto droneDto) {
		StringBuilder error = new StringBuilder();
					
		if (!Validator.isValidSerialNumber(droneDto.getSerialNumber())) {
			error.append("- The 'serialNumber' is too large. \n");
		}

		if (droneDto.getWeightLimit() > 500 || droneDto.getWeightLimit() < 0) {
			error.append("- The 'weightLimit' must be between 1 and 500. \n");
		}

		if (droneDto.getBatteryCapacity() > 100 || droneDto.getBatteryCapacity() < 0) {
			error.append("- The 'batteryCapacity' must be between 0 and 100. \n");
		}
		
		if (droneDto.getState().equals(DroneState.LOADING) && droneDto.getBatteryCapacity() < 25) {
			error.append("- The 'state' can't be LOADING while the 'batteryCapacity' is less than 25. \n");
		}
		return error.toString();
	}
	
	public Integer medicationWeight(Drone drone) {
		List<Medication> list = drone.getMedications();
		
		if(list == null || list.isEmpty()) {
			return 0;
		}
		
		int weight = 0;
		for (Medication medication : list) {
			weight += medication.getWeight();
		}
		return weight;
	}
	
	public DroneState nextState(Drone drone) {
		DroneState actual = drone.getState();
		switch (actual) {
		case IDLE:
			return DroneState.LOADING;
		case LOADING:
			return DroneState.LOADED;
		case LOADED:
			return DroneState.DELIVERING;
		case DELIVERING:
			return DroneState.DELIVERED;
		case DELIVERED:
			return DroneState.RETURNING;
		case RETURNING:
			return DroneState.IDLE;
		default:
			return DroneState.IDLE;
		}
	}

}
