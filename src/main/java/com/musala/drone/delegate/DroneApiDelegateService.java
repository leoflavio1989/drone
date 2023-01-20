package com.musala.drone.delegate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.drone.exception.NotFoundException;
import com.musala.drone.DroneApiDelegate;
import com.musala.drone.exception.DroneException;
import com.musala.drone.mapper.DroneMapper;
import com.musala.drone.mapper.MedicationMapper;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDto;
import com.musala.drone.model.GenericResponseDto;
import com.musala.drone.model.Medication;
import com.musala.drone.model.MedicationDto;
import com.musala.drone.repository.IDroneRepo;
import com.musala.drone.repository.IMedicationRepo;
import com.musala.drone.service.DroneService;
import com.musala.drone.type.DroneState;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DroneApiDelegateService implements DroneApiDelegate {

	private final IDroneRepo droneRepo;
	private final DroneMapper droneMapper;
	private final DroneService droneService;
	private final IMedicationRepo medicationRepo;
	private final MedicationMapper medicationMapper;

	@Autowired
	public DroneApiDelegateService(IDroneRepo droneRepo, DroneMapper droneMapper, DroneService droneService,
			IMedicationRepo medicationRepo, MedicationMapper medicationMapper) {
		this.droneRepo = droneRepo;
		this.droneMapper = droneMapper;
		this.droneService = droneService;
		this.medicationRepo = medicationRepo;
		this.medicationMapper = medicationMapper;
	}

	@Override
	public ResponseEntity<DroneDto> createDrone(DroneDto droneDto) {

		// ------ validate data
		String error = droneService.validateDroneData(droneDto);
		if (!error.isEmpty()) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "Input Errors: \n" + error);
		}
		// ------- end validate data

		Drone drone = droneMapper.droneDtoToDrone(droneDto);
		drone = droneRepo.save(drone);
		droneDto = droneMapper.toDto(drone);
		log.info("Drone " + drone.getSerialNumber() + " created.");
		return new ResponseEntity<>(droneDto, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<List<DroneDto>> getAllDrones() {
		List<Drone> drones = droneRepo.findAll();
		List<DroneDto> dto = droneMapper.toDtos(drones);
		return ResponseEntity.ok(dto);
	}

	@Override
	public ResponseEntity<DroneDto> getDrone(Integer droneId) {

		Drone drone = droneRepo.findById(droneId)
				.orElseThrow(() -> new NotFoundException("The 'droneId' doesn't exist."));
		DroneDto dto = droneMapper.toDto(drone);
		return ResponseEntity.ok(dto);
	}

	@Override
	public ResponseEntity<DroneDto> updateDrone(DroneDto droneDto) {		
		
		Drone drone = droneRepo.findById(droneDto.getId())
				.orElseThrow(() -> new NotFoundException("The 'droneId' doesn't exist."));
		
		DroneState state = droneService.nextState(drone);
		
		// ------ validate data
		String error = droneService.validateDroneData(droneDto);

		if (!error.isEmpty()) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "Input Errors: \n" + error);
		}
		// ------- end validate data

		drone = droneMapper.droneDtoToDrone(droneDto);
		if(!state.equals(drone.getState())) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "The 'state' should be the next state of the drone, in this case '"+ state + "'.");
		}
		
		drone = droneRepo.save(drone);
		droneDto = droneMapper.toDto(drone);
		log.info("Drone " + drone.getSerialNumber() + " updated.");

		return ResponseEntity.ok(droneDto);
	}

	@Override
	public ResponseEntity<GenericResponseDto> loadMedicationToDrone(Integer medicationId, Integer droneId) {
		Drone drone = droneRepo.findById(droneId)
				.orElseThrow(() -> new NotFoundException("The 'droneId' does't exist."));
		Medication medication = medicationRepo.findById(medicationId)
				.orElseThrow(() -> new NotFoundException("The 'medicationId' does't exist."));
		List<Medication> list = drone.getMedications();
		if (list == null || list.isEmpty()) {
			list = new ArrayList<>();
		}
		if (droneService.medicationWeight(drone) + medication.getWeight() > drone.getWeightLimit()) {
			throw new DroneException(HttpStatus.BAD_REQUEST, "Overloaded drone");
		}
		medication.setDrone(drone);		
		GenericResponseDto dto = new GenericResponseDto();
		try {
			medicationRepo.save(medication);
			dto.setMessage("Medication loaded");
		} catch (Exception e) {
			dto.setMessage("Error loading medication");
		}

		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<List<MedicationDto>> getDroneMedications(Integer droneId) {
		Drone drone = droneRepo.findById(droneId)
				.orElseThrow(() -> new NotFoundException("The 'droneId' doesn't exist."));
		List<Medication> list = drone.getMedications();
		return ResponseEntity.ok(medicationMapper.toDtos(list));
	}
	
	@Override
	public ResponseEntity<List<DroneDto>> getAvailableDrones() {
		List<Drone> drones = droneRepo.findByState(DroneState.IDLE);
		List<DroneDto> dto = droneMapper.toDtos(drones);
		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<GenericResponseDto> updateDroneState(Integer droneId) {
		Drone drone = droneRepo.findById(droneId)
				.orElseThrow(() -> new NotFoundException("The 'droneId' doesn't exist."));
		GenericResponseDto dto = new GenericResponseDto();
		DroneState state = droneService.nextState(drone);
		
		if(state.equals(DroneState.LOADING) && drone.getBatteryCapacity() < 25) {
			dto.setMessage("The 'state' can't be LOADING while the 'batteryCapacity' is less than 25.");
			return ResponseEntity.ok(dto);
		}
		
		drone.setState(state);
		
		try {
			droneRepo.save(drone);
			dto.setMessage("State changed");
		} catch (Exception e) {
			dto.setMessage("Error changing state");
		}
		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<GenericResponseDto> getDroneBattery(Integer droneId) {
		Drone drone = droneRepo.findById(droneId)
				.orElseThrow(() -> new NotFoundException("The 'droneId' doesn't exist."));
		int battery = drone.getBatteryCapacity();
		
		GenericResponseDto dto = new GenericResponseDto();
		
		dto.setMessage("The battery level of the drone is " + battery + "%.");
		return ResponseEntity.ok(dto);
	}

}
