package com.musala.drone.repository;

import com.musala.drone.model.Checks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChecksRepo extends JpaRepository<Checks, Integer> {
}
