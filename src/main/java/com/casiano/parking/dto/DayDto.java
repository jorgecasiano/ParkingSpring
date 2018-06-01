package com.casiano.parking.dto;

/**
 * Dto used to represents a day of the week
 * @author jorge
 *
 */
public class DayDto {

	private Long id;
    private String name;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
