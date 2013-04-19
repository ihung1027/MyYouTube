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
	<h3>Video Upload:</h3>
	<br>Select a file to upload: <br />
	<form action="UploadServlet" method="post" enctype="multipart/form-data" onsubmit="return check()" >
		<input type="file" id="uploadfilename" name="file" size="50" />
		<br />
		<input type="submit" value="Upload File" />
	</form>
	<div><a href = "list.jsp">Go to List</div>
	<script type="text/javascript"> 
		function check()
		{
		     var name = document.getElementById("uploadfilename").value;
		     var pos= name.lastIndexOf(".");
		     var suffix = name.substring(pos).toLowerCase();
		    if(suffix==".mp4"||suffix==".mp3"||suffix==".flv")
		   	{		    	
		    	 return true;
		    }		     	
		     else
		    {
		    	 alert("The format of uploaded file must be .mp3/4 or .flv");
		    	 return false;
		    }
		    	
		}
</script>
</body>