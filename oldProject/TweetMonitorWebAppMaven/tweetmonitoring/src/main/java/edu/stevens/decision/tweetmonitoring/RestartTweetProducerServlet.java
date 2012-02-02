package edu.stevens.decision.tweetmonitoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestartTweetProducerServlet extends RootServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestartTweetProducerServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties properties = getProperties();

		resp.sendRedirect(properties.getProperty("websiteUrl"));

		URL scriptUrl = this.getClass().getResource(
				properties.getProperty("findProducerPidScript"));
		
		ProcessBuilder pb = new ProcessBuilder(scriptUrl.getFile(),
				properties.getProperty("testProducerPath"));
		pb.start();

		scriptUrl = this.getClass().getResource(
				properties.getProperty("restartTweetScript"));

		pb = new ProcessBuilder(scriptUrl.getFile(),
				properties.getProperty("testProducerPath"),
				properties.getProperty("testProducerXmsArg"),
				properties.getProperty("testProducerXmxArg"),
				properties.getProperty("testProducerJar"));
		pb.start();
	}
}