package com.weathersimulator.model;

public class Airport {
 	
	private Integer id;
	private String name;
	private String iataCd; 
	private int climateZoneId;
	private double latitude;
	private double longitude;
	private double altitude; 
	private double sunriseAt;
	private double sunsetAt;
	private String stateCode;
	private String localTime;

	private double temperature;
	private String condition;
	private double humidity;
	private double pressure;
	
	

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIataCd() {
		return iataCd;
	}

	public void setIataCd(String iataCd) {
		this.iataCd = iataCd;
	}

	
	
	public int getClimateZoneId() {
		return climateZoneId;
	}

	public void setClimateZoneId(int climateZoneId) {
		this.climateZoneId = climateZoneId;
	}

/*	public double getSummerLow() {
		return summerLow;
	}

	public void setSummerLow(double summerLow) {
		this.summerLow = summerLow;
	}

	public double getSummerHigh() {
		return summerHigh;
	}

	public void setSummerHigh(double summerHigh) {
		this.summerHigh = summerHigh;
	}

	public double getWinterLow() {
		return winterLow;
	}

	public void setWinterLow(double winterLow) {
		this.winterLow = winterLow;
	}

	public double getWinterHigh() {
		return winterHigh;
	}

	public void setWinterHigh(double winterHigh) {
		this.winterHigh = winterHigh;
	}

	public double getRainLow() {
		return rainLow;
	}

	public void setRainLow(double rainLow) {
		this.rainLow = rainLow;
	}

	public double getRainHigh() {
		return rainHigh;
	}

	public void setRainHigh(double rainHigh) {
		this.rainHigh = rainHigh;
	}*/

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
		
	}


	public String getLocalTime() {
		return localTime;
	}

	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}


	public double getSunriseAt() {
		return sunriseAt;
	}

	public void setSunriseAt(double sunriseAt) {
		this.sunriseAt = sunriseAt;
	}

	public double getSunsetAt() {
		return sunsetAt;
	}

	public void setSunsetAt(double sunsetAt) {
		this.sunsetAt = sunsetAt;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", iataCd=" + iataCd +", stateCode=" + stateCode+ "<br>";
	}
}