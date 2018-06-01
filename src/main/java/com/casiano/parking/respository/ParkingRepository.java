package com.casiano.parking.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casiano.parking.model.Parking;

/**
 * Parking repository
 * @author jorge
 *
 */
public interface ParkingRepository extends JpaRepository<Parking, Long>, ParkingRepositoryCustom {
	
}
