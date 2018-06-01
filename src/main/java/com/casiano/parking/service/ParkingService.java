package com.casiano.parking.service;

import java.util.List;

import com.casiano.parking.dto.ParkingDto;
import com.casiano.parking.dto.ParkingMessageDto;

/**
 * Parking service that allows managing the parking system
 * @author jorge
 *
 */
public interface ParkingService {

	/**
	 * Get all parkings
	 * @return list of parkings
	 */
	public List<ParkingDto> getAll();
	
	/**
	 * Add new parking
	 * @param parkingDto contains parking information
	 * @return message with the result of adding
	 */
	public ParkingMessageDto add(ParkingDto parkingDto);
	
	/**
	 * Get parking by id
	 * @param id of parking
	 * @return parking information
	 */
	public ParkingDto getById(Long id);
	
	/**
	 * Edit existing parking
	 * @param parkingDto contains parking information to edit
	 * @return message with the result of editing
	 */
	public ParkingMessageDto edit(ParkingDto parkingDto);
	
	/**
	 * Get the filtered parkings
	 * @param date and hour of the open parking with format DD.MM.yyyy#H (example 25.05.2018#12)
	 * @param complete filter parkings with spaces
	 * @param latitude in decimal coordinates
	 * @param longitude in decimal coordinates
	 * @param distance in kilometers
	 * @return list of parkings
	 */
	public List<ParkingDto> filter(final String date, final Boolean complete, final Double latitude, final Double longitude, Double distance);
	
	/**
	 * Liberate space of parking with id
	 * @param id of parking
	 * @return message with information about the result
	 */
	public ParkingMessageDto liberateParking(Long id);
	
	/**
	 * Reserve space of parking with id
	 * @param id of parking
	 * @return message with information about the result
	 */
	public ParkingMessageDto reserveParking(Long id);
	
}
