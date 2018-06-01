package com.casiano.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used for parameter problems in requests
 * @author jorge
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -4356986636569481231L;

	public BadRequestException(String message) {
		super(message);
	}
	
}
