package com.mycompany.testproducer;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class App {

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		logInfo("Producer started");

		Connection conn = null;

		Properties properties = new Properties();
		try {

			logInfo("Loading properties...");
			properties
					.load(App.class.getResourceAsStream("/config.properties"));

			logInfo("Opening RabbitMQ connection...");
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(properties.getProperty("RabbitMQHost"));
			conn = factory.newConnection();

			logInfo("Creating new channel...");
			Channel chan = conn.createChannel();
			conn.addShutdownListener(new ShutdownListener() {

				public void shutdownCompleted(ShutdownSignalException cause) {
					logError(cause);
				}
			});
			chan.queueDeclare("twitterstream", false, false, false, null);

			logInfo("Creating new listener...");
			StatusListener listener = new Producer(chan, conn);

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey(properties.getProperty("OAuthConsumerKey"))
					.setOAuthConsumerSecret(
							properties.getProperty("OAuthConsumerSecret"))
					.setOAuthAccessToken(
							properties.getProperty("OAuthAccessToken"))
					.setOAuthAccessTokenSecret(
							properties.getProperty("OAuthAccessTokenSecret"));
			Configuration config = cb.build();

			logInfo("Configure twitter listener...");
			Twitter twitter = new TwitterFactory(config).getInstance();
			twitter.verifyCredentials();

			TwitterStream twitterStream = new TwitterStreamFactory(config)
					.getInstance();
			twitterStream.addListener(listener);
		
			logInfo("Twitter stream listened...");
			twitterStream.sample();
	
		} catch (IOException ioe) {
			logError(ioe);
		} catch (TwitterException te) {
			logError(te);
		} catch (Throwable t) {
			logError(t);
		}
	}

	private static void logError(Throwable t) {
		if (logger.isEnabledFor(Level.ERROR)) {
			logger.error(t.getMessage(), t);
		}
	}

	private static void logInfo(String message) {
		if (logger.isEnabledFor(Level.INFO)) {
			logger.info(message);
		}
	}
}