package com.casiano.parking.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.casiano.parking.model.Parking;
import com.casiano.parking.respository.ParkingRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingRepositoryTest {

	@Autowired
	private ParkingRepository parkingRepository;


	@Test
	public void verifyFindParkingInRepositoryFilterOk() {

		List<Parking> parkings = parkingRepository.filterAdvanced(false, 7, 1);

		assertThat(parkings.size(), is(1));
		assertThat(parkings.get(0).getId(), is(3L));
		assertThat(parkings.get(0).getName(), is("Parking Plasencia - Plasencia")); 
	}


}