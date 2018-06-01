package com.casiano.parking.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Dto used to represents a parking
 * @author jorge
 *
 */
public class ParkingDto {

	private Long id;
    private String name;
    private int openingHour;
    private int closingTime;
    private int totalSpace;
    private int freeSpace;
    private Set<DayDto> days = new HashSet<DayDto>();
    
    private Double latitude;
    private Double longitude;
    
	public ParkingDto() {
		super();
	}
	
	public ParkingDto(Long id, String name, int openingHour, int closingTime, int totalSpace, int freeSpace, Set<DayDto> days,
			Double latitude, Double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.openingHour = openingHour;
		this.closingTime = closingTime;
		this.totalSpace = totalSpace;
		this.freeSpace = freeSpace;
		this.days = days;
		this.latitude = latitude;
		this.longitude = longitude;
	}

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
	public Integer getOpeningHour() {
		return openingHour;
	}
	public void setOpeningHour(Integer openingHour) {
		this.openingHour = openingHour;
	}
	public Integer getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(Integer closingTime) {
		this.closingTime = closingTime;
	}
	public Integer getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}
	public Integer getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(Integer freeSpace) {
		this.freeSpace = freeSpace;
	}

	public Set<DayDto> getDays() {
		return days;
	}
	public void setDays(Set<DayDto> days) {
		this.days = days;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "ParkingDto [id=" + id + ", name=" + name + ", openingHour=" + openingHour + ", closingTime="
				+ closingTime + ", totalSpace=" + totalSpace + ", freeSpace=" + freeSpace + ", days=" + days
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	
}
