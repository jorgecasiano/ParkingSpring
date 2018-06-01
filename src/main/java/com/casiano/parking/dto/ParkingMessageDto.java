package com.casiano.parking.dto;

/**
 * Dto used to display the api information messages
 * @author jorge
 *
 */
public class ParkingMessageDto {
	
	private Long id;
	private String message;
	
	public ParkingMessageDto(Long id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ParkingMessageDto [id=" + id + ", message=" + message + "]";
	}

}
