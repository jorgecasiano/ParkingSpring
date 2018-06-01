package com.casiano.parking.service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.casiano.parking.dto.ParkingDto;
import com.casiano.parking.dto.ParkingMessageDto;
import com.casiano.parking.exception.BadRequestException;
import com.casiano.parking.exception.ParkingNotFoundException;
import com.casiano.parking.exception.ParkingSpaceException;
import com.casiano.parking.message.ErrorMessage;
import com.casiano.parking.message.InfoMessage;
import com.casiano.parking.model.Parking;
import com.casiano.parking.respository.ParkingRepository;
import com.casiano.parking.util.MapperUtil;
import com.casiano.parking.util.ParkingUtil;


@Service
@Transactional(propagation = Propagation.NEVER)
public class ParkingServiceImpl implements ParkingService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);
	
	/**
	 * Default distance in Kms
	 */
	private static final double DEFAULT_DISTANCE = 5.0;
	
	@Autowired
	ParkingRepository parkingRepository;

	@Override
	public List<ParkingDto> getAll() {
		return parkingRepository.findAll().stream().map(it -> MapperUtil.toDto(it)).collect(Collectors.toList());
	}

	@Override
	public List<ParkingDto> filter(final String date, final Boolean complete, final Double latitude, final Double longitude, Double distance) {
		Calendar calendar = ParkingUtil.getCalendarFromDayOrToday(date);
		if (calendar == null) {
			throw new BadRequestException(ErrorMessage.FORMAT_ERROR_PARAMETER_DATE);
		} else {
			int dayOfWeek = ParkingUtil.getDayOfWeek(calendar);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			logger.info("Filter parkings with day "+dayOfWeek+" and hour "+hour);
			boolean onlyAvailable = !(complete == null || complete.booleanValue());
			List<Parking> parkings = parkingRepository.filterAdvanced(onlyAvailable, dayOfWeek, hour);
			if (latitude != null && longitude != null) {
				Double distanceKm = (distance != null) ? distance:DEFAULT_DISTANCE;
				parkings = parkings.stream().filter(parking -> ParkingUtil.distanceKms(parking, latitude, longitude) < distanceKm).collect(Collectors.toList());
			}
			if (!parkings.isEmpty()) {
				return parkings.stream().map(it -> MapperUtil.toDto(it)).collect(Collectors.toList());
			} else {
				logger.error(ErrorMessage.NO_PARKINGS_FOUND_WITH_FILTER_OPTIONS);
				throw new ParkingNotFoundException(ErrorMessage.NO_PARKINGS_FOUND_WITH_FILTER_OPTIONS);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ParkingMessageDto add(ParkingDto parkingDto) {
		return MapperUtil.toParkingMessageDto(parkingRepository.save(MapperUtil.toModel(parkingDto)), InfoMessage.PARKING_SUCCESSFULLY_SAVED);
	}


	@Override
	public ParkingDto getById(Long id) {
		return MapperUtil.toDto(findByIdOrTrhowException(id));
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ParkingMessageDto liberateParking(Long id) {
		ParkingMessageDto parkingMessageDto = null;
		Parking parking = findByIdOrTrhowException(id);
		if (parking != null && parking.getFreeSpace() < parking.getTotalSpace()) {
			parking.setFreeSpace(parking.getFreeSpace()+1);
			parkingRepository.save(parking);
			parkingMessageDto = MapperUtil.toParkingMessageDto(parking, InfoMessage.LIBERATED_PLACE_PARKING);
		} else {
			logger.error(ErrorMessage.ALL_SPACES_FREE);
			throw new ParkingSpaceException(ErrorMessage.ALL_SPACES_FREE);
		}

		return parkingMessageDto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ParkingMessageDto reserveParking(Long id) {
		ParkingMessageDto parkingMessageDto = null;
		Parking parking = findByIdOrTrhowException(id);
		if (parking != null && parking.getFreeSpace() > 0) {
			parking.setFreeSpace(parking.getFreeSpace()-1);
			parkingRepository.save(parking);
			parkingMessageDto = MapperUtil.toParkingMessageDto(parking, InfoMessage.RESERVED_PARKING_SPACE);
		} else {
			logger.error(ErrorMessage.NO_SPACES_FREE);
			throw new ParkingSpaceException(ErrorMessage.NO_SPACES_FREE);
		}

		return parkingMessageDto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ParkingMessageDto edit(ParkingDto parkingDto) {
		findByIdOrTrhowException(parkingDto.getId());
		return MapperUtil.toParkingMessageDto(parkingRepository.save(MapperUtil.toModel(parkingDto)), InfoMessage.PARKING_SUCCESSFULLY_MODIFIED);
	}

	
	private Parking findByIdOrTrhowException(Long id) {
		if (id != null) {
			return parkingRepository.findById(id).orElseThrow(() -> {
				logger.error(ErrorMessage.NO_PARKING_FOUND_WITH_ID+id);
				return new ParkingNotFoundException(ErrorMessage.NO_PARKING_FOUND_WITH_ID+id);
			});
		} else {
			logger.error(ErrorMessage.ID_NOT_PROVIDED);
			throw new BadRequestException(ErrorMessage.ID_NOT_PROVIDED);
		}
	}
}
