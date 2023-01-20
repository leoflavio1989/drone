package com.musala.drone.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.drone.MedicationApiDelegate;
import com.musala.drone.exception.DroneException;
import com.musala.drone.exception.NotFoundException;
import com.musala.drone.mapper.MedicationMapper;
import com.musala.drone.model.Medication;
import com.musala.drone.model.MedicationDto;
import com.musala.drone.repository.IMedicationRepo;
import com.musala.drone.service.MedicationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicationApiDelegateService implements MedicationApiDelegate {

	private final IMedicationRepo medicationRepo;
	private final MedicationMapper medicationMapper;
	private final MedicationService medicationService;

	@Autowired
	public MedicationApiDelegateService(MedicationService medicationService, IMedicationRepo medicationRepo,
			MedicationMapper medicationMapper) {
		this.medicationRepo = medicationRepo;
		this.medicationMapper = medicationMapper;
		this.medicationService = medicationService;

	}

	@Override
	public ResponseEntity<MedicationDto> createMedication(MedicationDto medicationDto) {

		// ------ validate data
		String error = medicationService.validateMeditationData(medicationDto);
		if (!error.isEmpty()) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "Input Errors: \n" + error);
		}
		// ------- end validate data

		Medication medication = medicationMapper.medicationDtoToMedication(medicationDto);
		medication = medicationRepo.save(medication);
		medicationDto = medicationMapper.toDto(medication);
		log.info("Madication " + medication.getName() + " created.");
		return new ResponseEntity<>(medicationDto, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<List<MedicationDto>> getAllMedications() {
		List<Medication> medications = medicationRepo.findAll();
		List<MedicationDto> dto = medicationMapper.toDtos(medications);
		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<MedicationDto> getMedication(Integer medicationId) {
				
		Medication medication = medicationRepo.findById(medicationId)
				.orElseThrow(() -> new NotFoundException("The 'medicationId' does't exist."));
		MedicationDto dto = medicationMapper.toDto(medication);
		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<MedicationDto> updateMedication(MedicationDto medicationDto) {
		// ------ validate data
		String error = medicationService.validateMeditationData(medicationDto);
		if (!error.isEmpty()) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "Input Errors: \n" + error);
		}
		// ------- end validate data

		Medication medication = medicationMapper.medicationDtoToMedication(medicationDto);
		medication = medicationRepo.save(medication);
		medicationDto = medicationMapper.toDto(medication);
		log.info("Madication " + medication.getName() + " updated.");
		return ResponseEntity.ok(medicationDto);
	}

}
