package edu.stevens.decision.tweetmonitoring;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefreshPendingTweetServlet extends RootServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties properties = getProperties();
		
		resp.sendRedirect(properties.getProperty("websiteUrl"));
	}
	
}
