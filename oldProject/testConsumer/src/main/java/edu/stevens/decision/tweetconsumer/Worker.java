/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stevens.decision.tweetconsumer;

import com.rabbitmq.client.QueueingConsumer.Delivery;
import edu.stevens.decision.tweetconsumer.hibernate.entities.Tweet;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import twitter4j.Status;

/**
 *
 * @author michaelpereira
 */
public class Worker implements Runnable
{

  Delivery _delivery;
  Session _session;

  Worker(Delivery delivery, Session openSession)
  {
    _session = openSession;
    _delivery = delivery;
  }

  public void run()
  {
    ObjectInputStream ois = null;
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(_delivery.getBody());
      ois = new ObjectInputStream(bais);
      Status status = (Status) ois.readObject();
      //System.out.println(" [x] Received " + status.getText());
      saveToDatabase(status);
      _session.close();
      ois.close();
      bais.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (ois != null)
          ois.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void saveToDatabase(Status status)
  {
    Transaction transaction = _session.beginTransaction();
    Tweet t = new Tweet();
    t.setTweetId(status.getId());
    t.setTweetUser(new Long(status.getUser().getId()));
    t.setTweetUserLogin(status.getUser().getName());
    t.setTweetDate(status.getCreatedAt());
    t.setTweetText(status.getText());
    if (status.isRetweet()) {
      t.setTweetRetweet(new Long(status.getRetweetedStatus().getId()));
    }
    if (status.getGeoLocation() != null) {
      t.setTweetLatitude(new Float(status.getGeoLocation().getLatitude()));
      t.setTweetLongitude(new Float(status.getGeoLocation().getLongitude()));
    }
    if (status.getPlace() != null) {
      t.setTweetPlace(status.getPlace().getFullName());
    }
    _session.save(t);
    transaction.commit();
  }
}
