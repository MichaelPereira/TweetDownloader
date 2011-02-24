package com.mycompany.testproducer;

import com.rabbitmq.client.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class App
{

  public static void main(String[] args)
          throws java.io.IOException
  {
    Connection conn = null;
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("jnickers12.mgnt.stevens-tech.edu");
    conn = factory.newConnection();
    Channel chan = conn.createChannel();
    conn.addShutdownListener(new ShutdownListener()
    {

      public void shutdownCompleted(ShutdownSignalException cause)
      {
        System.err.println(cause.toString());
      }
    });
    chan.queueDeclare("testqueue", false, false, false, null);
    StatusListener listener = new Producer(chan, conn);


    ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setOAuthConsumerKey("nHmnTH8xHKTTWl6IxdX6Qg")
//                .setOAuthConsumerSecret("KieSvlnLyWcTWiRoyT0oVsGHYbbYl6YxeZ5fdMUho")
//                .setOAuthAccessToken("94156943-sII2dGBjmjkSpWvooya6ysvbKRzfy7ZKJSIz5S1db")
//                .setOAuthAccessTokenSecret("OObKVEvH2CcWDlheeA3WPksPSKqAu1L4xyxrTdGVXf8");
    //TwitterStream twitterStream = new TwitterStreamFactory(cb.build(), listener).getInstance();
    //twitterStream.sample();


    cb.setOAuthConsumerKey("NDZZHc1d6FqDtjryq212g").setOAuthConsumerSecret("uUvlrUTqCymjUdBvkWcpwQ5iIj599nONZSozNvtBPeU").setOAuthAccessToken("250478866-g4Yx32VLxAkgjUNFkMpSdEf179j3h44VcM2lbGev").setOAuthAccessTokenSecret("YSWx0Ni1gkKq15HbKLUAZ4jtT25vMLjwMTZygAVPd4");

    TwitterListener twitterlistener = new SearchListener();
//    AsyncTwitterFactory twitterfactory = new AsyncTwitterFactory(cb.build(), twitterlistener);
//    AsyncTwitter asyncTwitter = twitterfactory.getInstance();

    Paging paging = new Paging(1, 100);

//    asyncTwitter.getUserTimeline(213461076, paging);

    TwitterFactory fact = new TwitterFactory(cb.build());
    Twitter t = fact.getInstance();
    processUserId(t, paging, chan, conn);

  }

  private static void processUserId(Twitter t, Paging paging, Channel chan, Connection conn)
  {
    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://jnickersmacpro1.mgnt.stevens-tech.edu:3306/arabictweets?useUnicode=true&characterEncoding=UTF8";
      java.sql.Statement stmt;
      ResultSet rs;
      java.sql.Connection mysqlCon = DriverManager.getConnection(url, "lsa", "stigmergy");

      stmt = mysqlCon.createStatement(
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);

      rs = stmt.executeQuery("SELECT id,tweet_user "
              + "from arabicusers ORDER BY id");
      int i = 0;
      while (rs.next()) {
        ++i;
        java.lang.Long userLong = rs.getLong("tweet_user");
        int userId = userLong.intValue();
        getTweets(t, userId, paging, chan, conn);
        if ((i % 340) == 0) {
          Thread.sleep(3600000);
        }
      }
    } catch (InterruptedException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static void getTweets(Twitter t, int userId, Paging paging, Channel chan, Connection conn) throws InterruptedException
  {
    try {
      ResponseList userTimeline = t.getUserTimeline(userId, paging);

      for (Object obj : userTimeline) {
        Status status = (Status) obj;
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
    } catch (TwitterException ex) {
      if (ex.exceededRateLimitation()) {
        Thread.sleep(1800000);
        return;
      }
      if (ex.isCausedByNetworkIssue())
        {
        Thread.sleep(30000);
        return;
      }
    }
  }
}
