package com.casiano.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used when there are no parkings
 * @author jorge
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException  {
	
	private static final long serialVersionUID = -7020659777424139676L;

	public ParkingNotFoundException(String message) {
		super(message);
	}
}
