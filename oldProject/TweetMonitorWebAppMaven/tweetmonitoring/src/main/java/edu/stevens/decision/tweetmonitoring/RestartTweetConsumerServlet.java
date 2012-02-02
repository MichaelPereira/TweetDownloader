package edu.stevens.decision.tweetmonitoring;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestartTweetConsumerServlet extends RootServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestartTweetConsumerServlet() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties properties = getProperties();

		resp.sendRedirect(properties.getProperty("websiteUrl"));

		URL scriptUrl = this.getClass().getResource(
				properties.getProperty("findConsumerPidScript"));
		
		ProcessBuilder pb = new ProcessBuilder(scriptUrl.getFile(),
				properties.getProperty("testConsumerPath"));
		pb.start();

		scriptUrl = this.getClass().getResource(
				properties.getProperty("restartTweetScript"));

		pb = new ProcessBuilder(scriptUrl.getFile(),
				properties.getProperty("testConsumerPath"),
				properties.getProperty("testConsumerXmsArg"),
				properties.getProperty("testConsumerXmxArg"),
				properties.getProperty("testConsumerJar"));
		pb.start();
	}

}
