package com.musala.drone.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.drone.CheckApiDelegate;
import com.musala.drone.mapper.ChecksMapper;
import com.musala.drone.model.Checks;
import com.musala.drone.model.ChecksDto;
import com.musala.drone.repository.IChecksRepo;

@Service
public class ChecksApiDelegateService implements CheckApiDelegate {

	private final IChecksRepo checksRepo;
	private final ChecksMapper checksMapper;

	@Autowired
	public ChecksApiDelegateService(IChecksRepo checksRepo, ChecksMapper checksMapper) {
		this.checksRepo = checksRepo;
		this.checksMapper = checksMapper;
	}
	
	// List of battery checks
	@Override
	public ResponseEntity<List<ChecksDto>> getChecks() {
		List<Checks> checks = checksRepo.findAll();
		List<ChecksDto> dto = checksMapper.toDtos(checks);
		return ResponseEntity.ok(dto);
	}

}
