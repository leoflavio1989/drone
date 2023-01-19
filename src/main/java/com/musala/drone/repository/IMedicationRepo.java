package com.musala.drone.repository;

import com.musala.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicationRepo extends JpaRepository<Medication, Integer> {

}
