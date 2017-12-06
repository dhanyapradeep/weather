--DROP TABLE users IF EXISTS;
 
CREATE TABLE IF NOT EXISTS airports (
  Id varchar(6) PRIMARY KEY,
  iataCd  varchar(6),
  Name varchar(150) ,
  City varchar(100) ,
  Lat Decimal(9,6) ,
  Lon Decimal(9,6),
  Alt Decimal(15,2) ,
  StateCode varchar(2) not null
) ;

CREATE TABLE IF NOT EXISTS CLIMATE_ZONES (
  Id varchar(6) PRIMARY KEY,
  description  varchar(50)
) ;

CREATE TABLE IF NOT EXISTS STATE_ZONE_MAPPING (
  statecode varchar(2) ,
  climatezoneId  varchar(6) , 
 PRIMARY KEY(statecode,climatezoneId)
) ;

/*
CREATE TABLE IF NOT EXISTS ZONELIMITS (
  climatezoneId varchar(6) PRIMARY KEY,
  SUMMER_LOW  decimal(5),
SUMMER_HIGH  decimal(5),
  WINTER_LOW  decimal(5),
WINTER_HIGH  decimal(5),
RAINDESC varchar(100) ,
RAIN_LOW  decimal(5),
RAIN_HIGH  decimal(5)
) ;
*/