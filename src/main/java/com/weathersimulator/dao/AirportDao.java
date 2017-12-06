package com.weathersimulator.dao;

import java.util.List;

import com.weathersimulator.model.Airport;

public interface AirportDao {

	Airport findByName(String name);
	
	List<Airport> findAll();

}