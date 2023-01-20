package com.musala.drone.task;

import com.musala.drone.model.Checks;
import com.musala.drone.model.Drone;
import com.musala.drone.repository.IChecksRepo;
import com.musala.drone.repository.IDroneRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class CheckTask {
	private final IDroneRepo droneRepo;
	private final IChecksRepo checksRepo;

	public CheckTask(IChecksRepo checksRepo, IDroneRepo droneRepo) {
		this.droneRepo = droneRepo;
		this.checksRepo = checksRepo;
	}

	@Scheduled(cron = "${checkBatteryLevel}")
	@Async
	public void checkBatteryLevel() {
		List<Drone> list = droneRepo.findAll();
		for (Drone drone : list) {
			Checks check = new Checks();
			check.setDate(LocalDateTime.now());
			check.setDescription("[Drone: " + drone.getId() + ", Battery Level: " + drone.getBatteryCapacity() + "]");
			try {
				checksRepo.save(check);
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
		}
	}
}
