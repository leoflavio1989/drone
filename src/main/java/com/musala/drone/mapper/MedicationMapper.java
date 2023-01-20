package com.musala.drone.mapper;

import com.musala.drone.model.Medication;
import com.musala.drone.model.MedicationDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

	MedicationDto toDto(final Medication medication);

	List<MedicationDto> toDtos(final List<Medication> medications);

	@InheritInverseConfiguration
	Medication medicationDtoToMedication(final MedicationDto medicationDto);
}
