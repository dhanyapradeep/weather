package com.weathersimulator.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weathersimulator.model.Airport;
import com.weathersimulator.util.CommonConstants;

@Repository
public class AirportDaoImpl implements AirportDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public Airport findByName(String name) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        
		//String sql = "SELECT * FROM airports A WHERE A.name=:name";
		
		String sql = 
				/*"  select  A.id, A.iataCd iataCd, A.name name,  A.statecode statecode  ,A.lat, A.lon, A.ALT, C.SUMMER_LOW, C.SUMMER_HIGH, C.WINTER_LOW, C.WINTER_HIGH, "+ 
				" C.RAIN_LOW,C.RAIN_HIGH, B.statecode, b.climatezoneid  from airports  A, STATE_ZONE_MAPPING B ,  ZONELIMITS C  "+
				 " where A.StateCode=B.statecode "+
				 " AND C.climatezoneId=B.climatezoneId and A.iataCd is not null and length(a.iatacd) > 0 and  A.name=:name " ; */
				
				 "  select  A.id, A.iataCd iataCd, A.name name,  A.statecode statecode  ,A.lat, A.lon, A.ALT  , "+ 
				 "   B.statecode, b.climatezoneid  from airports  A, STATE_ZONE_MAPPING B  "+
				 " where A.StateCode=B.statecode "+
				 " and A.iataCd is not null and length(a.iatacd) > 0 and  A.name=:name " ;  
		
        Airport result = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    new AirportMapper());
                    
        
        
        return result;
        
	}

	@Override
	public List<Airport> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
 
	
		String sql = 
				/*"  select  top 10 A.id, A.iataCd iataCd, A.name name,  A.statecode statecode  ,A.lat, A.lon, A.ALT, C.SUMMER_LOW, C.SUMMER_HIGH, C.WINTER_LOW, C.WINTER_HIGH, "+ 
				" C.RAIN_LOW,C.RAIN_HIGH, B.statecode, b.climatezoneid  from airports  A, STATE_ZONE_MAPPING B ,  ZONELIMITS C  "+
				 " where A.StateCode=B.statecode "+
				 " AND C.climatezoneId=B.climatezoneId and A.iataCd is not null and length(a.iatacd) > 0 order by rand() " ;*/
				"  select  top 10 A.id, A.iataCd iataCd, A.name name,  A.statecode statecode  ,A.lat, A.lon, A.ALT , "+ 
				"  B.statecode, b.climatezoneid  from airports  A, STATE_ZONE_MAPPING B  "+
				 " where A.StateCode=B.statecode "+
				 " AND  A.iataCd is not null and length(a.iatacd) > 0 order by rand() " ;
				
				List<Airport> result = namedParameterJdbcTemplate.query(sql, params, new AirportMapper());
        
        return result;
        
	}

	private static final class AirportMapper implements RowMapper<Airport> {

		public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
			Airport airport = new Airport();
			airport.setId(rs.getInt("id"));
			airport.setName(rs.getString("name"));
			airport.setIataCd(rs.getString("iataCd"));
			airport.setStateCode(rs.getString("statecode"));	
			

			airport.setLatitude(rs.getDouble("lat"));
			airport.setLongitude(rs.getDouble("lon"));
			airport.setAltitude(rs.getDouble("alt"));
			
			airport.setClimateZoneId(rs.getInt("climatezoneid"));
			airport.setSunriseAt(CommonConstants.SUNRISE_TIME);
			airport.setSunriseAt(CommonConstants.SUNSET_TIME);
			   
			return airport;
		}
	}

}