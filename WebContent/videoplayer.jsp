<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<% String[] videoNames = request.getParameterValues("videoname");
	String name = videoNames[0];	
%>
<TITLE>Play Video</TITLE>	

<!--  This HTML file plays an .mp4 or .flv media file using JW Player 6.
The following code is from the Longtail Video Setup Wizard at 
http://www.longtailvideo.com/support/jw-player-setup-wizard.
-->

<!-- Call the JW Player JavaScript file, jwplayer.js. 
Replace DOWNLOAD-DISTRIBUTION-DOMAIN-NAME with the domain name of your 
CloudFront download distribution, for example, d1234.cloudfront.net 
(begins with "d"). This causes a browser to download the JW Player file 
before streaming begins.
-->

<script type='text/javascript' src='jwplayer/jwplayer.js'></script>

</HEAD>

<BODY>
<H1><%=name %></H1>
<div id='mediaplayer'><a href="list.jsp">Return to List</a></div>
<!-- Replace STREAMING-DISTRIBUTION-DOMAIN-NAME with the domain name of your 
streaming distribution, for example, s5678.cloudfront.net (begins with "s").

Replace VIDEO-FILE-NAME with the name of your .mp4 or .flv video file, 
including the .mp4 or .flv filename extension. For example, if you uploaded 
my-vacation.mp4, enter my-vacation.mp4.
-->


<script type="text/javascript"> 
   jwplayer('mediaplayer').setup({
      file: "rtmp://s3r9p2jbtxwe63.cloudfront.net/cfx/st/<%=name%>",
      width: "720",
      height: "480"
   });
</script>

</BODY>
</HTML>