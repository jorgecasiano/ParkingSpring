package com.casiano.parking.respository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.casiano.parking.model.Parking;

@Repository
public class ParkingRepositoryCustomImpl implements ParkingRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	private final static String SELECT_FILTER_PARKING = "select P.* from PARKING as P inner join PARKING_DAY as PD where P.PARKING_ID = PD.PARKING_ID ";
	
	@Override
	public List<Parking> filterAdvanced(boolean onlyAvailable, Integer day, Integer hour) {
		final StringBuilder sqlQuery = new StringBuilder(SELECT_FILTER_PARKING);
		sqlQuery.append(" and PD.DAY_ID = :day and P.OPENING_HOUR <= :hour and P.CLOSING_TIME > :hour");
		if (onlyAvailable) {
			sqlQuery.append(" and P.FREE_SPACE > 0");
		}
		Query queryFilter = entityManager.createNativeQuery(sqlQuery.toString(), Parking.class);
		queryFilter.setParameter("day", day);
		queryFilter.setParameter("hour", hour);
		
		return queryFilter.getResultList();
	}
}