package com.mycompany.testproducer;

import com.rabbitmq.client.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class App {

    public static void main(String[] args)
            throws IOException {
        Connection conn = null;

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            System.err.println(e);
        }
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getProperty("RabbitMQHost"));
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
        cb.setOAuthConsumerKey(properties.getProperty("OAuthConsumerKey"))
                .setOAuthConsumerSecret(properties.getProperty("OAuthConsumerSecret"))
                .setOAuthAccessToken(properties.getProperty("OAuthAccessToken"))
                .setOAuthAccessTokenSecret(properties.getProperty("OAuthAccessTokenSecret"));
        Configuration config = cb.build();
        Twitter twitter = new TwitterFactory(config).getInstance();
        try {
            User verifyCredentials = twitter.verifyCredentials();
        } catch (TwitterException ex) {
            System.err.println(ex.getErrorMessage());
            return;
        }
        
        TwitterStream twitterStream = new TwitterStreamFactory(config).getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}
