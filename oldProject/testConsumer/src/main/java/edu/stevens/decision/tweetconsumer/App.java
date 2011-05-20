package edu.stevens.decision.tweetconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import edu.stevens.decision.tweetconsumer.hibernate.utils.HibernateUtil;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.hibernate.classic.Session;

public class App
{

  public static void main(String[] args)
          throws java.io.IOException,
          java.lang.InterruptedException,
          ClassNotFoundException
  {
    try {
      Connection conn = null;
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("jnickersmacpro1.mgnt.stevens-tech.edu");
      conn = factory.newConnection();
      Channel chan = conn.createChannel();
      chan.queueDeclare("twitterstream", false, false, false, null);
      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
      QueueingConsumer consumer = new QueueingConsumer(chan);
      chan.basicConsume("twitterstream", true, consumer);
      ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(5);
      int i = 0;
      while (i < 1000) {
        Session openSession = HibernateUtil.getSessionFactory().openSession();
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        Worker newWorker = new Worker(delivery, openSession);
        newCachedThreadPool.execute(newWorker);
        ++i;
        System.out.println(i);
      }
        System.out.println("done");
      newCachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("waited");
      Runtime.getRuntime().exit(1);
    } catch (OutOfMemoryError e) {
      throw e;
    } catch (org.hibernate.exception.ConstraintViolationException e)
    {
      
    } catch (org.hibernate.exception.GenericJDBCException e) {}
    catch (org.hibernate.exception.DataException e) {}
  }
}
