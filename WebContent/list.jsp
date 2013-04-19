<%@page import="java.io.*, java.util.*, manager.*,com.amazonaws.services.s3.model.*,com.amazonaws.services.s3.*,java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title></title>
</head>
<body>
	 <table>
	 <tr><td>Videos</td> <td>rate</td><td>Your Rate</td></tr>
  <%
  		RDSManager rdsManager = new RDSManager();
  		ResultSet rs = rdsManager.readObjectRatings();
  		
  		while(rs.next())
  		{
  			String name = rs.getString("FileName");
  			float rate = 0;
  			if(rs.getFloat("Rate")!= 0)
  				rate = rs.getFloat("Rate");
  	
  			out.print("<tr>");
  			out.print("<tr><td><a href =\"videoplayer.jsp?videoname="+name+"\">"+name+"</td>");
  			
  			out.print("<form action=\"UpdateServlet\" method = \"post\">");
  			out.print("<td><input type = \"text\" name = \"averageRate\" value="+ rate +" readonly></td>");
  			out.print("<td><input type = \"text\" name = \"rate\" value="+ 0 +"></td>");
  			out.print("<td><input type = \"submit\" value = \"update\"></td>");
  			out.print("<input type=\"hidden\" name = \"fileName\" value = \"" + name +"\">");
  			out.print("</form>");
  			
  			out.print("<form action=\"DeleteServlet\" method = \"post\">");
  			out.print("<td><input type = \"submit\" value = \"delete\"></td>");
  			out.print("<input type=\"hidden\" name = \"fileName\" value = \"" + name +"\">");
  			out.print("</form>");
  			out.print("</tr>"); 			
  		}
  %> 
  	</table>
  	<div><a href = "index.jsp"> return to uploading page</div>
</body>
</html>