package com.mycompany.testproducer;

import com.rabbitmq.client.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class App 
{
    public static void main( String[] args )
     throws java.io.IOException {
         Connection conn = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        conn = factory.newConnection();
        Channel chan = conn.createChannel();
        conn.addShutdownListener(new ShutdownListener() {

            public void shutdownCompleted(ShutdownSignalException cause) {
                System.err.println(cause.toString());
            }
        });
        chan.queueDeclare("twitterstream", false, false, false, null);
        StatusListener listener = new Producer(chan, conn);


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("nHmnTH8xHKTTWl6IxdX6Qg")
                .setOAuthConsumerSecret("KieSvlnLyWcTWiRoyT0oVsGHYbbYl6YxeZ5fdMUho")
                .setOAuthAccessToken("94156943-sII2dGBjmjkSpWvooya6ysvbKRzfy7ZKJSIz5S1db")
                .setOAuthAccessTokenSecret("OObKVEvH2CcWDlheeA3WPksPSKqAu1L4xyxrTdGVXf8");
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build(), listener).getInstance();
        twitterStream.sample();
    }
}
