package com.weathersimulator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherUtilTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(WeatherUtilTest.class);
 
      
	    @Test
	    public void testTemperatureAtDifferentAltitudes() {
	    	Date date = new Date(); 
	    	double temperatureLowAltitude = WeatherUtils.getTemperature(23.112719, 96.0 , date,6,18);
	    	double temperatureHighAltitude = WeatherUtils.getTemperature(23.112719, 1684.0 , date,6,18);
	    	
	    	Assert.assertTrue("Temperature is higher at lower altitudes  .", temperatureLowAltitude>temperatureHighAltitude);
	    	 
	    }
	    
	    @Test
	    public void testTemperatureAtDifferentLatitudes() {
	    	Date date = new Date(); 
	    	double temperatureCloserToEquator = WeatherUtils.getTemperature(23.112719, 96.0 , date,6,18);
	    	double temperatureFartherFromEquator = WeatherUtils.getTemperature(25.440064, 96.0 , date,6,18);
	    	
	    	Assert.assertTrue("Temperature is higher closer to the equator.", temperatureCloserToEquator>temperatureFartherFromEquator);
	    	 
	    }
	    
	    
	    @Test
	    public void testTemperatureOnDifferentDates()  {
	    	DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
	    	Date dateOne = null;
	    	Date dateTwo = null;
			try {
				dateOne = format.parse("01-01-2017");
				dateTwo = format.parse("01-10-2017");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	double temperatureForOne = WeatherUtils.getTemperature(23.112719, 96.0 , dateOne,6,18);
	    	double temperatureForTwo = WeatherUtils.getTemperature(25.440064, 96.0 , dateTwo,6,18);
	    	
	    	
	    	Assert.assertTrue("Temperature is different on different dates.", temperatureForOne!=temperatureForTwo);
	    	 
	    }
	    
	    
	    @Test
	    public void testTemperatureAtDifferentTimes()  {
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
	    	double temperatureForTwo = WeatherUtils.getTemperature(23.112719, 96.0 , dateTwo,6,18);
	    	
	    	
	    	Assert.assertTrue("Temperature is lower in the early morning.", temperatureForOne<temperatureForTwo);
	    	 
	    }

	    
	    @Test
	    public void testSeason()  {	    	 
	    	int season = WeatherUtils.getSeason(11, 11);  
	    	Assert.assertTrue("Season changes with month and zone .", season == CommonConstants.WINTER);
	    }
	    
	    @Test
	    public void testPressure()  {	    	 
	    	double pressureAtAlt1 = WeatherUtils.getPressure(30, 23.112719, 96.0);
	    	double pressureAtAlt2 = WeatherUtils.getPressure(30, 23.112719, 190.0);
	    	Assert.assertTrue("Pressure is lower at higher altitudes.", pressureAtAlt2 < pressureAtAlt1);
	    }

 
}
