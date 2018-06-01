package com.casiano.parking.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Entity that contains the information of a parking
 * @author jorge
 *
 */
@Entity
public class Parking {

	@Id
	@Column(name = "PARKING_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    @Column(name = "OPENING_HOUR")
    private int openingHour;
    @Column(name = "CLOSING_TIME")
    private int closingTime;
    @Column(name = "TOTAL_SPACE")
    private int totalSpace;
    @Column(name = "FREE_SPACE")
    private int freeSpace;
    
    
    @ManyToMany
    @JoinTable(
      name = "PARKING_DAY", 
      joinColumns = { @JoinColumn(name = "PARKING_ID") }, 
      inverseJoinColumns = { @JoinColumn(name = "DAY_ID") }
    )
    private Set<Day> days = new HashSet<Day>();
    
    private Double latitude;
    private Double longitude;

    
	public Parking() {
		super();
	}
	
	public Parking(Long id, String name, int openingHour, int closingTime, int totalSpace, int freeSpace, Set<Day> days,
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

	public Set<Day> getDays() {
		return days;
	}
	public void setDays(Set<Day> days) {
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
	
}
