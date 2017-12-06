package com.weathersimulator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class that houses the logic for predicting the toy weather model
 * 
 * @author dhanyakairali
 *
 */
public class WeatherUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(WeatherUtils.class);


	/**
	 * Calculate the temperature based on the parameters provided. The temperature
	 * is dependent on the latitude, altitude, time of the year, time of the day etc
	 * 
	 * @param latitude
	 *            latitude of the airport
	 * @param altitude
	 *            altitude of the airport
	 * @param date
	 *            Date and time for which to calculate the temperature
	 * @param timeOfSunrise
	 *            The time of sunrise
	 * @param timeOfSunset
	 *            The time of sunset
	 * @return the temperature
	 */
	public static Double getTemperature(double latitude, double altitude, java.util.Date date, double timeOfSunrise,
			double timeOfSunset) {
		double temperature = CommonConstants.TEMP_MAX_INIT;
		// Factor in the effect of latitude . Decrease the temperature
		// with increase in latitude(distance from equator where temp is maximum)
		temperature -= CommonConstants.LATITUDE_EFFECT_RATE * latitude;

		// Factor in the effect of altitude
		// Slight tweaking to the formula for the effect of altitude on temperature
		// from https://www.grc.nasa.gov/www/k-12/airplane/atmosmet.html
		// T =15.04 - 0.00649 * h(in metre)
		temperature -= 
				CommonConstants.ALTITUDE_EFFECT_ON_TEMP_FACTOR 
				* altitude 
				* CommonConstants.FEET_TO_METRE_CONVERSION_FACTOR;

		// Add season effect
		// Assuming that the temperature follows a sine curve pattern
		// Formula for standard sine curve
		double lagInSeasonEffect = 0;
		// y(t) = A * sin(2*PI*f+phase)
		temperature += getSinEffect(
				CommonConstants.SEASONAL_EFFECT_AMPLITUDE, 
				(CommonUtils.getMonth(date) + 1) / 12, lagInSeasonEffect);

		// Effect of time of day on the temperature
		// The temperature will be at the lowest just before sunrise and will be
		// at its highest 2-3 hours from noon.
		double tempMax = temperature;
		double tempMin = temperature - CommonConstants.TEMP_MAX_MIN_RANGE;
		// Period during which there is sunlight
		double duration = timeOfSunset - timeOfSunrise;
		// Assume maximum temperature slightly after middle of the day
		double maxTempAt = timeOfSunrise + duration / 2 + duration / 4;

		//LOGGER.debug("tempMax:" + tempMax);
		//LOGGER.debug("maxTempAt:" + maxTempAt);
		double timeOfDay = CommonUtils.getHour(date);

		//LOGGER.debug("timeOfDay:" + timeOfDay);

		// Temperature keeps increasing from sunrise until slightly
		// after middle of the day and then starts to fall.
		// Slope is y2-y1/x2-x1
		double slope = (tempMax - tempMin) / (maxTempAt - timeOfSunrise);
		if (timeOfDay >= maxTempAt) {
			temperature = tempMax - (slope * (timeOfDay - maxTempAt));
		} else {
			temperature = tempMin + (slope * (timeOfDay - timeOfSunrise));
		}
		//LOGGER.debug("temperature:" + temperature);

		// distance from the sea can also be considered when calculating temperature
		// Cities closer to the sea will have a lower temperature
		// TODO

		// double error = randomNumber * TEMPERATURE_ERROR_FACTOR;
		return Math.round(temperature *100D)/100D;
		 
	}

	static double getSinEffect(double amplitude, double frequency, double phase) {
		return Math.sin(2 * Math.PI * frequency + phase);
	}
 
	public static double getHumidity(int zoneId, int season, double temperature) {
 		// Humidity at a given temperature

		double humidity = 0;
		if (season == CommonConstants.MONSOON) {
			humidity = 80;
		}
		if (season == CommonConstants.PREMONSOON) {
			humidity = 60;
		}
		if (season == CommonConstants.WINTER) {
			humidity = 40;
		}
		if (season == CommonConstants.SUMMER) {
			humidity = 20;
		}
		
		humidity += getHumidityCorrectionForZone(zoneId);

		humidity += getHumidityCorrectionForTemperature(humidity, temperature);

		
		return Math.round(humidity *100D)/100D;

	}
	
	
	/**
	 * Make adjustment to the humidity based on the zone to 
	 * which the airport belongs
	 * */
	private static int getHumidityCorrectionForZone(int zoneId) {
		int humidityCorrection = 0;
		// Adjust humidity for zone
		// For zone 1-5(Continental)
		if (zoneId <= 5)
			humidityCorrection = 3;
		// For zone 6-8(Tropical)
		else if (zoneId <= 8)
			humidityCorrection = 5;
		// For zone 9-11(Tropical coastal)
		else if (zoneId <= 8)
			humidityCorrection = 10;		
		return humidityCorrection;
		
	}
	
	/**
	 * Calculate the correction required for humidity based on the temperature 
	 * Humidity decreases with rise in temperature  	 
	 * @param hummidity
	 * @param temmperature 
	 */
	private static double getHumidityCorrectionForTemperature(
			double humidity, double temperature) {
		double humidityCorrection = 0;
		// Effect of temperature on humidity
		// Relative humidity decreases with rise in temperature
		// With 6 degree decrease, humidity doubles
		// Calculation of variation in humidity
		double effectDueToTempChange = 
				(CommonConstants.NORMAL_TEMP_FOR_HUMIDITY_CALC - temperature) / 6;
		humidityCorrection =(humidity * effectDueToTempChange);		
		humidityCorrection = Math.abs(humidityCorrection);
		
		if ((humidity+humidityCorrection) > 100 || (humidity+humidityCorrection) < 0)
			humidityCorrection = 0;
		return humidityCorrection;
		
	}

	/**
	 * Atmospheric pressure as a function of the latitude and the altitude
	 * 
	 * @param temp
	 * @param month
	 * @param latitude
	 * @param altitude
	 * @return
	 */
	public static double getPressure(double temp, double latitude, double altitude) {
		//Start at Average sea level pressure
		double pressure = CommonConstants.AVERAGE_SEA_LEVEL_PRESSURE;
		
		// Correction of pressure based on latitude
		//India has latitude ranging between 37.6 and 8.4 
		//Near the equator the pressure is low Along 30 degree N and S are
		//the subtropical highs
		//Pressure is higher at latitudes close to 30 
		double pressureCorrectionDueToLatitude =  Math.abs(latitude - 30);
		pressure -= pressureCorrectionDueToLatitude;
		
		// Correction of pressure based on altitude
		// Pressure decreases by 12mbar for every 100 metres
		double pressureCorrectionDueToAltitude = 
				(12 * (altitude * CommonConstants.FEET_TO_METRE_CONVERSION_FACTOR) / 100);
		 
		pressure -= pressureCorrectionDueToAltitude;
		return Math.round(pressure *100D)/100D;
		 
	}

	public static String getConditions(
			int season, double temperature, double pressure, double humidity) {
		
		LOGGER.debug("season:"+season); 
		
		LOGGER.debug("temperature:"+temperature);
		LOGGER.debug("pressure:"+pressure);
		LOGGER.debug("humidity:"+humidity);
		
		String condition = "";
		if (season == CommonConstants.MONSOON)
			condition = WeatherCondition.RAIN.name();
		if (season == CommonConstants.PREMONSOON) {
			if (humidity > 80) {
				condition = WeatherCondition.RAIN.name();
			} else {
				condition = WeatherCondition.SUNNY.name();
			}
		}
		if (season == CommonConstants.SUMMER) {			 
				
				if (humidity > 80) {
					condition = WeatherCondition.RAIN.name();
				} 
				else {
					condition = WeatherCondition.SUNNY.name();
				}
			 
		}
		if (season ==  CommonConstants.WINTER) { 
			if(temperature < 10) {
				condition = WeatherCondition.SNOW.name();
			}
			else {
				condition = WeatherCondition.SUNNY.name();
			} 
		}

		return condition;
	}
	/*
	 * The season is a function of the month and the zone in which the airport lies.
	 * 
	 * @param month The month from the date for which we are predicting the weather
	 * 
	 * @param zoneId The zone of the airport
	 * 
	 * @return A constant representing the season
	 */
	public static int getSeason(int month, int zoneId) {
		
		LOGGER.debug("getSeason:"+month+":"+zoneId);
		int result = CommonConstants.SUMMER;
 
		if (month >= 12) {
			month = month - 12;

		}
		LOGGER.debug("month:"+month);
		switch (month) {
		// The season for months 0-2(Jan-March)
		case 0:
		case 1:
		case 2:
			// Cycle over the seasons for the different zones.
			if (zoneId <= 3)
				result = CommonConstants.WINTER;
			else if (zoneId <= 5)
				result = CommonConstants.SUMMER;
			else if (zoneId <= 8)
				result = CommonConstants.PREMONSOON;
			else if (zoneId <= 11)
				result = CommonConstants.MONSOON;
			break;

		case 3:
		case 4:
		case 5:
			if (zoneId <= 3)
				result = CommonConstants.MONSOON;
			else if (zoneId <= 5)
				result = CommonConstants.WINTER;
			else if (zoneId <= 8)
				result = CommonConstants.SUMMER;
			else if (zoneId <= 11)
				result = CommonConstants.PREMONSOON;
			break;
		case 6:
		case 7:
		case 8:
			if (zoneId <= 3)
				result = CommonConstants.PREMONSOON;
			else if (zoneId <= 5)
				result = CommonConstants.MONSOON;
			else if (zoneId <= 8)
				result = CommonConstants.WINTER;
			else if (zoneId <= 11)
				result = CommonConstants.SUMMER;
			break;
		case 9:
		case 10:
		case 11:
			LOGGER.debug("zoneId:"+zoneId);
			if (zoneId <= 3) {
				result = CommonConstants.SUMMER;
			}
			else if (zoneId <= 5) {
				result = CommonConstants.PREMONSOON;
			}
			else if (zoneId <= 8) {
				result = CommonConstants.MONSOON;
			}
			else if (zoneId <= 11) {
				result = CommonConstants.WINTER;
			}
			LOGGER.debug("result:"+result);
			break;
		default:
		}

		return result;

	}
	public static void main(String args[]) {
		DateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
    	Date dateOne = null;
    	Date dateTwo = null;
		try {
			dateOne = format.parse("01-01-2017 06:00:00");
			dateTwo = format.parse("01-01-2017 13:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	double temperatureForOne = WeatherUtils.getTemperature(23.112719, 96.0 , dateOne,6,18);
    	System.out.println("temperatureForOne:"+temperatureForOne);
    	double temperatureForTwo = WeatherUtils.getTemperature(25.440064, 96.0 , dateTwo,6,18);
    	System.out.println("temperatureForTwo:"+temperatureForTwo);
		
	}

}
