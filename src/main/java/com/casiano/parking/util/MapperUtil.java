package com.casiano.parking.util;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casiano.parking.dto.DayDto;
import com.casiano.parking.dto.ParkingDto;
import com.casiano.parking.dto.ParkingMessageDto;
import com.casiano.parking.exception.ParkingNotFoundException;
import com.casiano.parking.message.ErrorMessage;
import com.casiano.parking.model.Day;
import com.casiano.parking.model.Parking;

/**
 * Utility class with the mapping between dto and models and vice versa
 * @author jorge
 *
 */
public class MapperUtil {

	private static final Logger logger = LoggerFactory.getLogger(MapperUtil.class);

	/**
	 * Create parking message from a parking and a message
	 * @param parking
	 * @param message
	 * @return message with info
	 */
	public static ParkingMessageDto toParkingMessageDto(Parking parking, final String message) {
		if (parking != null) {
			ParkingMessageDto parkingMessage = new ParkingMessageDto(parking.getId(), message);
			logger.info("Response: "+parkingMessage);
			return parkingMessage;
		} else {
			logger.error(ErrorMessage.NOT_FOUND_PARKING);
			throw new ParkingNotFoundException(ErrorMessage.NOT_FOUND_PARKING);
		}
	}

	/**
	 * Maps a model parking to dto
	 * @param parking to convert
	 * @return parkingDto
	 */
	public static ParkingDto toDto(Parking parking) {
		if (parking != null && parking.getId() != null) {
			ParkingDto parkingDto = new ParkingDto(parking.getId(), parking.getName(), 
					parking.getOpeningHour(), parking.getClosingTime(), parking.getTotalSpace(), 
					parking.getFreeSpace(), toDaysDto(parking.getDays()), parking.getLatitude(), parking.getLongitude());
			return parkingDto;
		} else {
			logger.error(ErrorMessage.NOT_FOUND_PARKING);
			throw new ParkingNotFoundException(ErrorMessage.NOT_FOUND_PARKING);
		}
	}

	/**
	 * Maps a dto parking to model
	 * @param parkingDto to convert
	 * @return parking
	 */
	public static Parking toModel(ParkingDto parkingDto) {
		if (parkingDto != null) {
			Parking parking = new Parking(parkingDto.getId(), parkingDto.getName(), 
					parkingDto.getOpeningHour(), parkingDto.getClosingTime(), parkingDto.getTotalSpace(), 
					parkingDto.getFreeSpace(), toDaysModel(parkingDto.getDays()), parkingDto.getLatitude(), parkingDto.getLongitude());
			return parking;
		} else {
			return null;
		}
	}

	private static Set<DayDto> toDaysDto(Set<Day> days) {
		if (days != null) {
			Set<DayDto> daysDto = new HashSet<DayDto>();
			for (Day day: days) {
				DayDto dayDto = new DayDto();
				dayDto.setId(day.getId());
				dayDto.setName(day.getName());
				daysDto.add(dayDto);
			}
			return daysDto;
		} else {
			return null;
		}
	}

	private static Set<Day> toDaysModel(Set<DayDto> days) {
		if (days != null) {
			Set<Day> daysModel = new HashSet<Day>();
			for (DayDto day: days) {
				Day dayModel = new Day();
				dayModel.setId(day.getId());
				dayModel.setName(day.getName());
				daysModel.add(dayModel);
			}
			return daysModel;
		} else {
			return null;
		}
	}


}
