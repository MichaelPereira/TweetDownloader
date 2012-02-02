<%@page import="java.io.FileInputStream"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.BufferedReader"%>
<html>
<body>
	<h2>Tweets monitoring home page</h2>

	<form name="restart tweet producer"
		action="http://localhost:8080/tweetmonitoring/RestartTweetProducerServlet/"
		method="get">
		<div align="left">
			<br>
			<input type="submit" value="Restart tweet producer"><br>
		</div>
	</form>

	<form name="restart tweet consumer"
		action="http://localhost:8080/tweetmonitoring/RestartTweetConsumerServlet/"
		method="get">
		<div align="left">
			<br>
			<input type="submit" value="Restart tweet consumer"><br>
		</div>
	</form>

</body>
</html>
