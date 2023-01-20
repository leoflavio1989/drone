package com.musala.drone.mapper;

import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DroneMapper {

	DroneDto toDto(final Drone drone);

	List<DroneDto> toDtos(final List<Drone> drones);

	@InheritInverseConfiguration
	Drone droneDtoToDrone(final DroneDto droneDto);
}
