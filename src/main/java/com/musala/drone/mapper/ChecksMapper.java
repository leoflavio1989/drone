package com.musala.drone.mapper;

import com.musala.drone.model.Checks;
import com.musala.drone.model.ChecksDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChecksMapper {

	ChecksDto toDto(final Checks checks);

	List<ChecksDto> toDtos(final List<Checks> checks);

	@InheritInverseConfiguration
	Checks medicationDtoToChecks(final ChecksDto checksDto);
}
