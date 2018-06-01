package com.casiano.parking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casiano.parking.dto.ParkingDto;
import com.casiano.parking.dto.ParkingMessageDto;
import com.casiano.parking.service.ParkingService;


/**
 * Rest API Parkings
 * 
 * @author jorge
 *
 */
@RestController
@RequestMapping("/parkings")
public class ParkingRestController {

	
	private static final Logger logger = LoggerFactory.getLogger(ParkingRestController.class);


	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ParkingService parkingService;
	
	/**
	 * Get all the parkings
	 * @return list of parkings
	 * @throws Exception if not find any parking
	 */
	@GetMapping
	public List<ParkingDto> getAll() throws Exception {
		loggerPath();
		logger.info("Returning all parkings");
		return parkingService.getAll();
	}

	/**
	 * Get the filtered parkings
	 * @param date and hour of the open parking with format DD.MM.yyyy#H (example 25.05.2018#12)
	 * @param complete filter parkings with spaces
	 * @param latitude in decimal coordinates
	 * @param longitude in decimal coordinates
	 * @param distance in kilometers
	 * @return list of parkings
	 */
	@GetMapping("/filter")
	public List<ParkingDto> filter(@RequestParam(required = false, value = "dia") String date, @RequestParam(required = false, value = "completo") Boolean complete,
			@RequestParam(required = false, value = "latitud") Double latitude, @RequestParam(required = false, value = "longitud") Double longitude, 
			@RequestParam(required = false, value = "distancia") Double distance) {
		loggerPath();
		logger.info("Filter parkings");
		return parkingService.filter(date, complete, latitude, longitude, distance);
	}

	/**
	 * Get parking from id
	 * @param id of parking
	 * @return parking
	 */
	@GetMapping("/{id}")
	public ParkingDto getById(@PathVariable("id") Long id) {
		loggerPath();
		logger.info("Parking id to return " + id);
		return parkingService.getById(id);
	}

	/**
	 * Add new parking
	 * @param parkingDto with parking data
	 * @return message with information about the result
	 */
	@PostMapping
	public ParkingMessageDto add(@RequestBody ParkingDto parkingDto) {
		loggerPath();
		logger.info("Parking to add " + parkingDto);
		return parkingService.add(parkingDto);
	}

	/**
	 * Edit existing parking
	 * @param parkingDto with parking data
	 * @return message with information about the result
	 */
	@PutMapping
	public ParkingMessageDto edit(@RequestBody ParkingDto parkingDto) {
		loggerPath();
		logger.info("Parking to edit " + parkingDto);
		return parkingService.edit(parkingDto);
	}

	/**
	 * Liberate space of parking with id
	 * @param id of parking
	 * @return message with information about the result
	 */
	@PutMapping("/{id}/liberate")
	public ParkingMessageDto liberateParking(@PathVariable("id") Long id) {
		loggerPath();
		logger.info("Parking id to liberate " + id);
		return parkingService.liberateParking(id);
	}

	/**
	 * Reserve space of parking with id
	 * @param id of parking
	 * @return message with information about the result
	 */
	@PutMapping("/{id}/reserve")
	public ParkingMessageDto reserveParking(@PathVariable("id") Long id) {
		loggerPath();
		logger.info("Parking id to reserve " + id);
		return parkingService.reserveParking(id);
	}


	private void loggerPath() {
		logger.info(request.getMethod()+" - Path: "+request.getRequestURI());
	}
	
}
