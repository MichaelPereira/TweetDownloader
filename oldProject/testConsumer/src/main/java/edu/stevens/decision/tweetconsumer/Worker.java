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
import org.hibernate.classic.Session;
import twitter4j.Status;

/**
 *
 * @author michaelpereira
 */
public class Worker implements Runnable {

    Delivery _delivery;
    Session _session;
    Session _session2;

    Worker(Delivery delivery, Session openSession, Session openSession2) {
        _session = openSession;
        _session2 = openSession2;
        _delivery = delivery;
    }

    public void run() {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(_delivery.getBody());
            ois = new ObjectInputStream(bais);
            Status status = (Status) ois.readObject();
            //System.out.println(" [x] Received " + status.getText());
            saveToDatabase(status);
            _session.close();
            _session2.close();
            ois.close();
            bais.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveToDatabase(Status status) {
        _session.beginTransaction();
        _session2.beginTransaction();
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
        _session.getTransaction().commit();
        _session2.save(t);
        _session2.getTransaction().commit();
    }
}
