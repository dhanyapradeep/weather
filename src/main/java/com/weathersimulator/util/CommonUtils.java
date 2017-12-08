package com.weathersimulator.util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.StringUtils;


/**
 * A utility class to be used by the weather game 
 */
public class CommonUtils {  
	
	/**
         * Get the month from a given date
         */
	public static int getMonth(Date date)  {		 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;
	}
	
	/**
         * Get the hour from a given date
         */
	public static int getHour(Date date)  {		 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour;
	} 
  
}
