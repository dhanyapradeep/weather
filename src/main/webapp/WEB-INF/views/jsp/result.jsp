<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
   
   
   <c:forEach var="airport" items="${airports}">   
    
   ${airport.name}, ${airport.stateCode} |
   ${airport.latitude}, ${airport.longitude} , ${airport.altitude}   | 
   ${airport.localTime} | 
   ${airport.condition} |
   ${airport.temperature} |
   ${airport.pressure} |	
   ${airport.humidity} 
   <br>
	
    
   </c:forEach>  
 