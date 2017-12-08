package com.weathersimulator.util;

/**
 * The possible weather conditions
 */
public enum WeatherCondition {

	SNOW("Snow"), SUNNY("Sunny"), RAIN("Rain");

	private String condition;

	WeatherCondition(String condition) {
		this.condition = condition;
	}

	public String condition() {
		return condition;
	}
}
