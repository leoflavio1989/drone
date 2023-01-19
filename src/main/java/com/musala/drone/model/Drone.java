package com.musala.drone.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.musala.drone.type.DroneModel;
import com.musala.drone.type.DroneState;

@Entity
@Table(name = "drone")
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(length = 100, nullable = false, name = "serial_number")
	private String serialNumber;

	@Column
	@Enumerated(value = EnumType.STRING)
	private DroneModel model;

	@Column(name = "weight_limit")
	private int weightLimit;

	@Column(name = "battery_capacity")
	private int batteryCapacity;

	@Column
	@Enumerated(value = EnumType.STRING)
	private DroneState state;


	// bi-directional one-to-many association to medication
	@OneToMany(mappedBy = "drone")
	private List<Medication> medications;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public DroneModel getModel() {
		return model;
	}

	public void setModel(DroneModel model) {
		this.model = model;
	}

	public int getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(int weightLimit) {
		this.weightLimit = weightLimit;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public DroneState getState() {
		return state;
	}

	public void setState(DroneState state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

}
