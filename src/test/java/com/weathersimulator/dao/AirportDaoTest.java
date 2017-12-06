package com.weathersimulator.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.weathersimulator.dao.AirportDao;
import com.weathersimulator.dao.AirportDaoImpl;
import com.weathersimulator.model.Airport;

public class AirportDaoTest {

    private EmbeddedDatabase db;

    AirportDao userDao;
    
    @Before
    public void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql")
    		.build();
    }

 
    
    @Test
    public void testFindAll() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	AirportDaoImpl airportDao = new AirportDaoImpl();
    	airportDao.setNamedParameterJdbcTemplate(template);
    	
    	List<Airport> airports = airportDao.findAll();  
    	Assert.assertEquals(10, airports.size()); 
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}