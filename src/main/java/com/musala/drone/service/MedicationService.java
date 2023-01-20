package com.musala.drone.service;

import org.springframework.stereotype.Service;

import com.musala.drone.model.MedicationDto;
import com.musala.drone.util.Validator;

@Service
public class MedicationService {

	public MedicationService() {

	}
	
	public String validateMeditationData(MedicationDto medicationDto) {
		StringBuilder error = new StringBuilder();
					
		if (!Validator.isValidMedicationName(medicationDto.getName())) {
			error.append("- The 'name' must have only letters, numbers, '-' and '_'. \n");
		}
		
		if (!Validator.isValidMedicationCode(medicationDto.getCode())) {
			error.append("- The 'code' must have only upper case letters, numbers and '_'. \n");
		}
		
		return error.toString();
	}
}
