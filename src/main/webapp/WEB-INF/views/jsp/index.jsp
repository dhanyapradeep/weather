<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<html>
<head>
<title>Input Parameters</title>
<script>

function check(){
	
	if(document.form1.fromDate.value == ''){
		alert("Please select From Date");
		document.form1.fromDate.focus();
		return false;
	} else { 
		document.form1.action="result.jsp"; 
		document.form1.submit();
	}			
}
</script>

<link href="./javascript/DataTables-1.9.4/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" media="all" />
<link type="text/css" rel="stylesheet" href="./css/cell_styles.css" />
	
<script src="./javascript/jquery.js" type="text/javascript" language="javascript"></script>
<script src="./javascript/jquery-ui-1.10.3/ui/jquery-ui.js" type="text/javascript" language="javascript"></script>
<script src="./javascript/jquery-ui-1.10.3/ui/jquery.ui.datepicker.js"></script>

<script type="text/javascript">
    	$(document).ready(function() {	    		
    	
			$( "#fromDate" ).datepicker({
				showOn: "button",
			   changeMonth: true,
			    changeYear: true,
			    buttonImage: "./javascript/jquery-ui-1.10.3/demos/datepicker/images/calendar.gif",
				buttonImageOnly: true,
				dateFormat:"dd/mm/yy",
				maxDate: "+0",
				onClose: function( selectedDate ) {
					//$( '#toDate' ).datepicker( "option", "minDate", selectedDate );
				 }			
			}); 
    	});		
    		
</script>    		
</head>

<body> 
 
<%
try {

  

%>
 
<center>
<form name="form1" method="post">
	<br/><br/>
	<div id="container">
	<table border="0" width="100%" class="pretty">
		<thead>
			<tr>
				<th colspan="2"><b>Weather simulator</b></th>
			</tr>
		</thead>
		<tbody>
   	<tr>
  		<td align=right><strong>Enter Date :</strong> <br/> [dd/mm/yyyy]</td>
    	<td  align="left">
    		<input type="text" name="fromDate" id="fromDate" value=<%=DateFormatUtils.format(new Date(), "dd/MM/yyyy")%> size="8" maxlength="10"/>   
    		 
    		 
   		</td>
   	</tr>
   	
   	   	<tr>
  		<td align=right><strong>No of Records:</td>
    	<td  align="left">
    		 
    		<input type="text" name="noOfRecords" id="noOfRecords"   size="8" maxlength="10"/>     		
    		 
   		</td>
   	</tr> 
   	
   <tr>
		<td colspan="2" align="center" bgcolor="#eeeedd">
		<input type="button" value="Submit" onclick="check()"/>			
		</td>
	</tr>
	</tbody>
	</table>
	</div>

</form>
<%}
catch(Exception e)
{
	out.println("Exception occured in input.jsp = " + e);
	e.printStackTrace();
}
finally
{
 
}
%> 

</center>
</body>
</html>
