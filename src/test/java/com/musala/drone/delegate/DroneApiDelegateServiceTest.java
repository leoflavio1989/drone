package com.musala.drone.delegate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.musala.drone.mapper.DroneMapper;
import com.musala.drone.mapper.MedicationMapper;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDto;
import com.musala.drone.repository.IDroneRepo;
import com.musala.drone.repository.IMedicationRepo;
import com.musala.drone.service.DroneService;
import com.musala.drone.type.DroneModel;
import com.musala.drone.type.DroneState;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringBootConfiguration
class DroneApiDelegateServiceTest {

	@Mock
	private IDroneRepo droneRepo;
	@Mock
	private DroneService droneService;
	@Mock
	private IMedicationRepo medicationRepo;
	@Mock
	private MedicationMapper medicationMapper;

	@Spy
	private DroneMapper droneMapper = Mappers.getMapper(DroneMapper.class);

	private DroneApiDelegateService droneApiDelegate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		droneApiDelegate = new DroneApiDelegateService(droneRepo, droneMapper, droneService, medicationRepo,
				medicationMapper);
	}

	@Test
	void whenCreateDroneShouldReturnSameDroneWithId() {
		Drone drone1 = new Drone();
		drone1.setSerialNumber("BDVB548DF");
		drone1.setBatteryCapacity(74);
		drone1.setWeightLimit(300);
		drone1.setModel(DroneModel.CRUISERWEIGHT);
		drone1.setState(DroneState.IDLE);

		Drone drone2 = new Drone();
		drone2.setId(20);
		drone2.setSerialNumber("BDVB548DF");
		drone2.setBatteryCapacity(74);
		drone2.setWeightLimit(300);
		drone2.setModel(DroneModel.CRUISERWEIGHT);
		drone2.setState(DroneState.IDLE);

		DroneDto droneDto = droneMapper.toDto(drone1);
		when(droneRepo.save(Mockito.any(Drone.class))).thenReturn(drone2);
		ResponseEntity<DroneDto> result = droneApiDelegate.createDrone(droneDto);
		assertTrue(result.hasBody());
		assertTrue(result.getBody().getId() == drone2.getId());
	}

	@Test
	void whenGetDroneShouldReturnADroneWithSpecificId() {
		Drone drone = new Drone();
		drone.setId(1);
		drone.setSerialNumber("SD54SD65");
		drone.setBatteryCapacity(60);
		drone.setWeightLimit(230);
		drone.setModel(DroneModel.HEAVYWEIGHT);
		drone.setState(DroneState.LOADING);

		when(droneRepo.findById(1)).thenReturn(Optional.of(drone));
		ResponseEntity<DroneDto> droneResponse = droneApiDelegate.getDrone(drone.getId());
		assertNotNull(droneResponse);
		assertTrue(droneResponse.getBody().getId() == drone.getId());
		assertTrue(droneResponse.getStatusCode() == HttpStatus.OK);
	}

	@Configuration
	static class Config {
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	}
}
