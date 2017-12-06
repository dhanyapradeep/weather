package com.weathersimulator.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weathersimulator.dao.AirportDao;
import com.weathersimulator.model.Airport;
import com.weathersimulator.util.CommonUtils;
import com.weathersimulator.util.WeatherUtils;

/**
 * The Spring controller class. This interprets the user input and transforms
 * the input into a sensible model which will be represented to the user by the
 * view.
 * 
 * @author Dhanya Kairali
 *
 */
@Controller
public class WelcomeController {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	// The Model
	@Autowired
	AirportDao airportDao;

	/**
	 * This method is called in the HTTP GET request for the url / Here, the model
	 * is loaded and the results are displayed in the "view"
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {

		List<Airport> airports = getAirportsWithWeatherInfo();
		model.addAttribute("airports", airports);
		return "result";

	}

	/**
	 * This method picks a few random airports and calculates different weather
	 * aspects of the respective airports. Airport information is stored in DB
	 * including the zone in which the airport falls, the altitude, latitude,
	 * longitude of these airports 
	 * 
	 * @return A list of airports
	 */
	public List<Airport> getAirportsWithWeatherInfo() {
		List<Airport> airports = null;

		airports = airportDao.findAll();

		// Get a random list of airports for which we will be showing the sample weather
		// data
		if (airports != null) {
			Iterator<Airport> iter = airports.iterator();

			while (iter.hasNext()) {

				Airport airport = iter.next();

				if (airport.getIataCd() != null && airport.getIataCd().trim().length() > 0) {
					predictWeather(airport, new Date());
				}
			}
		}

		return airports;
	}

	/**
	 * This method predicts the weather for a given airport for a given date and
	 * time. Weather prediction uses the following logic: For the given date, get
	 * the season in the state of the airport.The season can be different for the
	 * same time of the year for a state falling in a different zone.
	 * 
	 * Next, get the temperature. Temperature is a function of the season, the
	 * latitude and the altitude of the airport. Temperatures are warmer approaching
	 * the latitude and lower at higher altitudes
	 * 
	 * Also compute the pressure and the humidity based on the independent variables 
	 * 
	 * @param airport
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private void predictWeather(Airport airport, Date date) {

		// Get the season in the airport. This depends on the date for which we are
		// trying to simulate. Airports are classified as belonging to different 
		//climate zones.
		// All airports in a climate zone will be having the same weather at a certain
		// time of the year.		 
		int month = CommonUtils.getMonth(date);
		int season = WeatherUtils.getSeason(
						month, airport.getClimateZoneId());

		//Ideally, the sunrise and sunset times would be different for 
		//the different airports
		double sunrise = airport.getSunriseAt();
		double sunset = airport.getSunsetAt();
		
		double temperature = 
				WeatherUtils.getTemperature(
						airport.getLatitude(), 
						airport.getAltitude(), 
						date, 
						sunrise,
						sunset);

		double pressure = 
				WeatherUtils.getPressure(
						temperature, 
						airport.getLatitude(), 
						airport.getAltitude());
		double humidity = 
				WeatherUtils.getHumidity(
						airport.getClimateZoneId(), 
						season, 
						temperature);
		String weatherCondition = 
				WeatherUtils.getConditions(
						season, 
						temperature, 
						pressure, 
						humidity);

		airport.setTemperature(temperature);
		airport.setPressure(pressure);
		airport.setCondition(weatherCondition);
		airport.setHumidity(humidity);

		//TimeZone tz = TimeZone.getTimeZone("UTC");
		TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
		// Quoted "Z" to indicate UTC, no timezone offset
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); 
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		airport.setLocalTime(nowAsISO);
	}

}
