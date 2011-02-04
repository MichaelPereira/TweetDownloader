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
import twitter4j.*;

/**
 *
 * @author Michael
 */
public class Producer implements StatusListener
{

  Connection conn;
  Channel chan;

  public Producer(Channel ca, Connection co)
  {
    chan = ca;
    conn = co;
  }

  public void onStatus(Status status)
  {
    try {
      //String msg = "@" + status.getUser().getScreenName() + " - " + status.getText();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(status);
      byte[] serializedStatus = baos.toByteArray();
      if (chan.isOpen()) {
        chan.basicPublish("", "testqueue", MessageProperties.TEXT_PLAIN, serializedStatus);
      } else {
        chan = conn.createChannel();
        chan.basicPublish("", "testqueue", MessageProperties.TEXT_PLAIN, serializedStatus);
      }

    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }

  }

  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
  {
    System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
  }

  public void onTrackLimitationNotice(int numberOfLimitedStatuses)
  {
    System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
  }

  public void onScrubGeo(int userId, long upToStatusId)
  {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  public void onException(Exception ex)
  {
    ex.printStackTrace();
  }
}
