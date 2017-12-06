# Weather Simulator
* The sample application generates weather data for different airports in India. 
* The basic premise is that India is divided into different climatic regions.  Airports belonging to different states are assigned different climatic regions.  
(Stamp’s classification https://www.pmfias.com/climatic-regions-of-india-stamps-koeppens-classification/)

### Weather Prediction module:
Weather is predicted for the current date and time fo 10 random airports.
The following information about the locations for which we are creating the simulation for are available to us:
Latitude, Longitude and elevation 

#### Calculation of temperature 
Calculated based on latitude, elevation, distance from sea, season, time of day
* Assume a temperature minimum level.
* Add season effect which changes following a sine curve pattern for the months of the year.
* Add effect of latitude - decrease one degree celsius for  every degree latitude (or 0.5 degrees) 
* Add effect of altitude (refer https://www.grc.nasa.gov/www/k-12/airplane/atmosmet.html) 
T =15.04 - 0.00649 * h(in metre) 
* Add effect of time of day
Based on the sunrise and sunset timings, identify time of max and min temperature
and derive the temperature  for the specific time of day. 
* Add effect of distance from the sea(not included in this model)
Add 1  degree celsius for every 100 km distance from the sea
 
#### Calculation of Pressure  
Calculated based on latitude, elevation, temperature , humidity 
* Average sea level pressure = 1013.25 mbar (https://en.wikipedia.org/wiki/Atmospheric_pressure) 
* Correct average sea level pressure for latitude - Hadleys 3 cell (Wikipedia  Atmospheric circulation)
* Correct for altitude - pressure decreases by about 1.2 kPa(1 kpa = 10mbar) for every 100 metres 
pressure decreases by 12 mbar for every 100 metres
 
#### Calculation of humidity  
Calculated based on temperature, season, zone effect
Humidity doubles with each 20 degree Fahrenheit decrease (http://ocw.usu.edu/Forest__Range__and_Wildlife_Sciences/Wildland_Fire_Management_and_Planning/unit4.htm) 

### 1. Technologies used
* Maven 3.0
* Spring 4.1.6.RELEASE
* HSQLDB 2.3.2
* Embedded Jetty server
 

### 2. To Run this project locally
* Install Java
* Install Maven  
 
Download the repository from github and run using Maven 

```
$ git clone https://github.com/dhanyakairali/weathersample
$ mvn jetty:run
```
 

Access the webapplication 

```
http://localhost:8080/weathersimulator/
```

To run tests

```
$ mvn test
```
 
### 3. To import this project into Eclipse IDE
```
$ mvn eclipse:eclipse
```
* Import into Eclipse via **existing projects into workspace** option.
* Done. 
 
### 4. Other references
https://github.com/mkyong/spring-embedded-database
Example for Spring MVC embedded DB 

 



 





