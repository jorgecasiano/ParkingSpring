package com.casiano.parking.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casiano.parking.controller.ParkingRestController;
import com.casiano.parking.model.Parking;

/**
 * Utilities used in the system of parkgins
 * @author jorge
 *
 */
public class ParkingUtil {

	private static final Logger logger = LoggerFactory.getLogger(ParkingRestController.class);
	
	/**
	 * Format date
	 */
	private static final String FORMAT_DATE = "^\\d{2}\\.\\d{2}\\.\\d{4}#\\d{2}$";
	
	/**
	 * Log message for the calculation of distances
	 */
	private static final String DISTANCE = "Distance calculated between (%s,%s) and (%s,%s) => %s Kms";
	
	/**
	 * Get a calendar with date of dateStr or current date
	 * @param dateStr with date in text format
	 * @return calendar
	 */
	public static Calendar getCalendarFromDayOrToday(final String dateStr) {
		Date date = null;
		Calendar c = null;
		if (dateStr != null) {
			logger.info("Get date of "+dateStr);
			if (dateStr.matches(FORMAT_DATE)) {
				DateFormat formatter = new SimpleDateFormat("dd.MM.yy#H");
				try {
					date = (Date) formatter.parse(dateStr);
				} catch (ParseException e) {
					logger.error("Parse format date");
				}
			}
		} else {
			date = new Date();
		}
		if (date != null) {
			c = Calendar.getInstance();
			c.setTime(date);
		}

		return c;
	}


	/**
	 * Get day of week from the calendar
	 * @param calendar
	 * @return day of week
	 */
	public static int getDayOfWeek(final Calendar calendar) {
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}

		return dayOfWeek;
	}


	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * @param parking with coordinate
	 * @param lat1 latitude of origin
	 * @param lon1 longitude of origin
	 * @return Distance in Kilometers
	 */
	public static double distanceKms(final Parking parking, double lat1, double lon1) {
		double lat2 = parking.getLatitude();
		double lon2 = parking.getLongitude();
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; // in KM
		logger.info(String.format(DISTANCE, lat1, lon1, lat2, lon2, dist));
		return (dist);
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
