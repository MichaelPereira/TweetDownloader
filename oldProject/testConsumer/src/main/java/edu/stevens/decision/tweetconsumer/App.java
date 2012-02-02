package edu.stevens.decision.tweetconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import edu.stevens.decision.tweetconsumer.hibernate.utils.HibernateUtil;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

public class App {

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		try {
			logInfo("Tweeter consumer. Loading properties...");
			Properties properties = new Properties();
			properties
					.load(App.class.getResourceAsStream("/config.properties"));

			logInfo("Connection to MQRabbit...");
			Connection conn = null;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(properties.getProperty("RabbitMQHost"));
			conn = factory.newConnection();

			logInfo("New channel on twitterstream...");
			Channel chan = conn.createChannel();
			chan.queueDeclare("twitterstream", false, false, false, null);

			logInfo(" [*] Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(chan);
			chan.basicConsume("twitterstream", true, consumer);
			ExecutorService newCachedThreadPool = Executors
					.newFixedThreadPool(5);

			for (int i = 0; i < 10000; ++i) {
				Session openSession = HibernateUtil.getSessionFactory()
						.openSession();
				Session openSession2 = HibernateUtil.getSessionFactory2()
						.openSession();

				QueueingConsumer.Delivery delivery = consumer.nextDelivery(20);
				if (delivery != null) {
					Worker newWorker = new Worker(delivery, openSession,
							openSession2);
					newCachedThreadPool.execute(newWorker);
				}
			}
			logInfo("Processed 10000. Thread pool closed...");
			newCachedThreadPool.shutdown();
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
