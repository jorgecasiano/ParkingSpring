package com.casiano.parking.respository;

import java.util.List;

import com.casiano.parking.model.Parking;


/**
 * Parking repository with custom operations
 * @author jorge
 *
 */
public interface ParkingRepositoryCustom {

	/**
	 * Filter the parkings by availability, day and hour
	 * @param onlyAvailable if true only parkings with free spaces
	 * @param day of the week that must be open
	 * @param hour of the day that must be open
	 * @return list of parkings
	 */
	public List<Parking> filterAdvanced(boolean onlyAvailable, Integer day, Integer hour);
}