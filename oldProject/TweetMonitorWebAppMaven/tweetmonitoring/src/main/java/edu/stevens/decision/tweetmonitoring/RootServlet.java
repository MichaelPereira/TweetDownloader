package edu.stevens.decision.tweetmonitoring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

public abstract class RootServlet extends HttpServlet {
	
	private final String propertyFileLocation = "/edu/stevens/decision/tweetmonitoring/tweetmonitoring.properties";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Properties getProperties() throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(propertyFileLocation);
		
		Properties properties = new Properties();
		properties.load(inputStream);

		return properties;
	}

}
