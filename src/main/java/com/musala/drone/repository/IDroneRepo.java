package com.musala.drone.repository;

import com.musala.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDroneRepo extends JpaRepository<Drone, Integer> {
}
