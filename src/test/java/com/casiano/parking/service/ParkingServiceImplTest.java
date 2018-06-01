package com.casiano.parking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.casiano.parking.dto.ParkingDto;
import com.casiano.parking.dto.ParkingMessageDto;
import com.casiano.parking.exception.ParkingNotFoundException;
import com.casiano.parking.exception.ParkingSpaceException;
import com.casiano.parking.message.InfoMessage;
import com.casiano.parking.model.Parking;
import com.casiano.parking.respository.ParkingRepository;

@RunWith(SpringRunner.class)
public class ParkingServiceImplTest {

	@TestConfiguration
	static class ParkingServiceImplTestContextConfiguration {

		@Bean
		public ParkingService parkingService() {
			return new ParkingServiceImpl();
		}
	}

	@Autowired
	private ParkingService parkingService;


	@MockBean
	private ParkingRepository parkingRepository;

	@Test
	public void verifyGetAllParking() {
		Parking parking = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		List<Parking> allParkings = Arrays.asList(parking);
		Mockito.when(parkingRepository.findAll()).thenReturn(allParkings);
		List<ParkingDto> parkingsDto = parkingService.getAll();
		assertThat(parkingsDto.size()).isEqualTo(1);
		assertThat(parkingsDto.get(0).getName()).isEqualTo("Parque Principe Felipe");
	}

	@Test
	public void verifyGetParkingByIdOk() {
		Parking parking = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		Mockito.when(parkingRepository.findById(1l)).thenReturn(Optional.of(parking));
		ParkingDto parkingDto = parkingService.getById(1L);
		assertThat(parkingDto.getName()).isEqualTo("Parque Principe Felipe");
	}

	@Test(expected = ParkingNotFoundException.class)
	public void verifyGetParkingByIdNotExistTrhowsException() {
		when(parkingRepository.findById(1l)).thenReturn(Optional.empty());
		parkingService.getById(1L);
	}


	@Test
	public void verifyLiberateParkingReturnMessageOk() {
		Parking parkingBeforeFree = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		Parking parkingFree = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 11, null, 0d, 0d);
		Mockito.when(parkingRepository.findById(1L)).thenReturn(Optional.of(parkingBeforeFree));
		Mockito.when(parkingRepository.save(parkingFree)).thenReturn(parkingFree);
		ParkingMessageDto parkingMessgeDto = parkingService.liberateParking(1L);
		assertThat(parkingMessgeDto.getMessage()).isEqualTo(InfoMessage.LIBERATED_PLACE_PARKING);
	}

	@Test(expected = ParkingSpaceException.class)
	public void verifyLiberateParkingAllLiberatedThrowParkingSpace() {
		Parking parkingAllLiberated = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 20, null, 0d, 0d);
		Mockito.when(parkingRepository.findById(1L)).thenReturn(Optional.of(parkingAllLiberated));
		parkingService.liberateParking(1L);
	}

	@Test
	public void verifyReserveParkingReturnMessageOk() {
		Parking parkingBeforeReserve = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		Parking parkingFree = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 11, null, 0d, 0d);
		Mockito.when(parkingRepository.findById(1L)).thenReturn(Optional.of(parkingBeforeReserve));
		Mockito.when(parkingRepository.save(parkingFree)).thenReturn(parkingFree);
		ParkingMessageDto parkingMessgeDto = parkingService.reserveParking(1L);
		assertThat(parkingMessgeDto.getMessage()).isEqualTo(InfoMessage.RESERVED_PARKING_SPACE);
	}

	@Test(expected = ParkingSpaceException.class)
	public void verifyReserveParkingWithoutSpacesThrowParkingSpace() {
		Parking parkingNoSpace = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 0, null, 0d, 0d);
		Mockito.when(parkingRepository.findById(1L)).thenReturn(Optional.of(parkingNoSpace));
		parkingService.reserveParking(1L);
	}

	@Test
	public void verifyGetFilterParkingNearReturnArray() {
		Parking parking = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		Mockito.when(parkingRepository.filterAdvanced(false, 2, 12)).thenReturn(Arrays.asList(parking));
		List<ParkingDto> parkingsDto = parkingService.filter("29.05.2018#12", true, 0.01, 0.01, 2d); // 2 km
		assertThat(parkingsDto.size()).isEqualTo(1);
		assertThat(parkingsDto.get(0).getName()).isEqualTo("Parque Principe Felipe");
	}

	@Test(expected = ParkingNotFoundException.class)
	public void verifyGetFilterParkingFarThrowNotFound() {
		Parking parking = new Parking(1L, "Parque Principe Felipe", 10, 20, 20, 10, null, 0d, 0d);
		Mockito.when(parkingRepository.filterAdvanced(false, 2, 12)).thenReturn(Arrays.asList(parking));
		parkingService.filter("29.05.2018#12", true, 0.01, 0.01, 1d); // 1 km
	}
}