/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproducer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import twitter4j.*;

/**
 * 
 * @author Michael
 */
public class Producer implements StatusListener {

	private Logger logger = Logger.getLogger(Producer.class);

	Connection conn;
	Channel chan;

	public Producer(Channel ca, Connection co) {
		chan = ca;
		conn = co;
	}

	public void onStatus(Status status) {
		try {
			// String msg = "@" + status.getUser().getScreenName() + " - " +
			// status.getText();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(status);
			byte[] serializedStatus = baos.toByteArray();
			if (chan.isOpen()) {
				chan.basicPublish("", "twitterstream",
						MessageProperties.TEXT_PLAIN, serializedStatus);
			} else {
				chan = conn.createChannel();
				chan.basicPublish("", "twitterstream",
						MessageProperties.TEXT_PLAIN, serializedStatus);
			}

		} catch (IOException ex) {
			logError(ex);
		}
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		logInfo("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	public void onScrubGeo(int userId, long upToStatusId) {
	}

	public void onException(Exception ex) {
		logError(ex);
	}

	public void onScrubGeo(long l, long l1) {
	}

	private void logError(Throwable t) {
		if (logger.isEnabledFor(Level.ERROR)) {
			logger.error(t.getMessage(), t);
		}
	}

	private void logInfo(String message) {
		if (logger.isEnabledFor(Level.INFO)) {
			logger.info(message);
		}
	}
}
