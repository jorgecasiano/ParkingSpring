package com.casiano.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used for parking space problems
 * @author jorge
 *
 */
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class ParkingSpaceException extends RuntimeException {
	
	private static final long serialVersionUID = -4612333701811032251L;

	public ParkingSpaceException(String message) {
		super(message);
	}

}
