package edu.stevens.decision.tweetconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import edu.stevens.decision.tweetconsumer.hibernate.utils.HibernateUtil;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.hibernate.classic.Session;

public class App {

    public static void main(String[] args)
            throws java.io.IOException,
            java.lang.InterruptedException,
            ClassNotFoundException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            Connection conn = null;
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(properties.getProperty("RabbitMQHost"));
            conn = factory.newConnection();
            Channel chan = conn.createChannel();
            chan.queueDeclare("twitterstream", false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(chan);
            chan.basicConsume("twitterstream", true, consumer);
            ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(5);


            for (int i = 0; i < 10000; ++i) {
                Session openSession = HibernateUtil.getSessionFactory().openSession();
                Session openSession2 = HibernateUtil.getSessionFactory2().openSession();
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Worker newWorker = new Worker(delivery, openSession, openSession2);
                newCachedThreadPool.execute(newWorker);
            }
            System.out.println("Processed 10000");
            newCachedThreadPool.shutdown();
            Runtime.getRuntime().exit(0);
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (org.hibernate.exception.ConstraintViolationException e) {
        } catch (org.hibernate.exception.GenericJDBCException e) {
        } catch (org.hibernate.exception.DataException e) {
        }
    }
}
