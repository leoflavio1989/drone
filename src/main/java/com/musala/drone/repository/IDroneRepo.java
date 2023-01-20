package com.musala.drone.repository;

import com.musala.drone.model.Drone;
import com.musala.drone.type.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDroneRepo extends JpaRepository<Drone, Integer> {
	List<Drone> findByState(DroneState state);
}
