package com.weathersimulator.util;

public class CommonConstants {
	public static final int MONSOON = 1;
	public static final int PREMONSOON = 2;
	public static final int WINTER = 3;
	public static final int SUMMER = 4;
	
	//Time of sunrise - default given to all airports
	public static final double SUNRISE_TIME = 6;
	//Time of sunset  - default given to all airports
	public static final double SUNSET_TIME = 18;
	
	//A default maximum temperature 
	public static final int TEMP_MAX_INIT = 45;
	
	//Effect of latitude on temperature 
	public static final double LATITUDE_EFFECT_RATE = 0.5;
	
	public static final double FEET_TO_METRE_CONVERSION_FACTOR = 0.3048;

	//The amplitude of the sin curve that is used to represent the seasonal effect
	// in degree celcius
	public static final int SEASONAL_EFFECT_AMPLITUDE = 5;
	public static final int TEMP_MAX_MIN_RANGE = 5;
	public static final int TEMPERATURE_ERROR_FACTOR = 3;
	
	//The base temperature for calculating humidity 
	public static final int NORMAL_TEMP_FOR_HUMIDITY_CALC = 30;
	
	//The base sea level pressure
	public static final double AVERAGE_SEA_LEVEL_PRESSURE = 1013.25;
	
	//The factor to apply for altitude effect on temperature
	public static final double ALTITUDE_EFFECT_ON_TEMP_FACTOR = 0.00649;
}
