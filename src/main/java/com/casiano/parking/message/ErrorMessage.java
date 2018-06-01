package com.casiano.parking.message;

/**
 * Class with the error messages used in the api
 * @author jorge
 *
 */
public class ErrorMessage {

	public static final String ALL_SPACES_FREE = "All parking spaces already released";
	public static final String NO_SPACES_FREE = "All parking spaces already reserved";
	public static final String NO_PARKING_FOUND_WITH_ID = "No parking found with id ";
	public static final String FORMAT_ERROR_PARAMETER_DATE = "Format error in the parameter 'dia', must be 'DD.MM.yyyy#HH' (example 31.05.2018#16)";
	public static final String NO_PARKINGS_FOUND_WITH_FILTER_OPTIONS = "No parkings found with filter options";
	public static final String ID_NOT_PROVIDED = "Parking id not provided";
	public static final String NOT_FOUND_PARKING = "Not found parking";
}
