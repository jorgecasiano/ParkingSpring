package com.casiano.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity that represents a day of the week
 * @author jorge
 *
 */
@Entity
public class Day {

	@Id
	@Column(name = "DAY_ID")
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
