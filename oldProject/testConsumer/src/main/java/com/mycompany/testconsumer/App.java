package com.mycompany.testconsumer;

import com.mysql.jdbc.Statement;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import twitter4j.Status;

public class App {

    public static void main(String[] args)
            throws java.io.IOException,
            java.lang.InterruptedException,
            ClassNotFoundException {

        Connection conn = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        conn = factory.newConnection();
        Channel chan = conn.createChannel();
        chan.queueDeclare("testqueue", false, false, false, null);
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://155.246.61.53:3306/newtweets?useUnicode=true&characterEncoding=UTF8";
        java.sql.Connection mysqlCon;
        try {
            mysqlCon = DriverManager.getConnection(url, "lsa", "stigmergy");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "INSERT INTO  `newtweets`.`tweet` (`tweet_id` ,`tweet_user` ,`tweet_user_login` ,`tweet_date` ,`tweet_text` ,`tweet_retweet` ,`tweet_latitude` ,`tweet_longitude` ,`tweet_place` ,`tweet_language`)VALUES (?,?,?,?,?,?,?,?,?,?);";
        System.out.println("URL: " + url);
        System.out.println("Connection: " + mysqlCon);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(chan);
        chan.basicConsume("testqueue", true, consumer);
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                ByteArrayInputStream bais = new ByteArrayInputStream(delivery.getBody());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Status status = (Status) ois.readObject();
                //System.out.println(" [x] Received " + status.getText());
                saveToDatabase(mysqlCon, sql, status);
                ois.close();
                bais.close();
            } catch (SQLException ex) {
                System.err.println(ex.getSQLState() + " - "+ ex.getLocalizedMessage());
            }
        }

    }

    private static void saveToDatabase(java.sql.Connection mysqlCon, String sql, Status status) throws SQLException {
        //java.sql.Statement stmt = mysqlCon.createStatement();
        PreparedStatement stmt = mysqlCon.prepareStatement(sql);
        stmt.setLong(1, status.getId());
        stmt.setLong(2, status.getUser().getId());
        stmt.setString(3, status.getUser().getName());
        stmt.setTimestamp(4, new Timestamp(status.getCreatedAt().getTime()));
        stmt.setString(5, status.getText());
        if (status.isRetweet()) {
            stmt.setLong(6, status.getRetweetedStatus().getId());
        } else {
            stmt.setNull(6, java.sql.Types.BIGINT);
        }
        if (status.getGeoLocation() != null) {
            stmt.setFloat(7, (float) status.getGeoLocation().getLatitude());
            stmt.setFloat(8, (float) status.getGeoLocation().getLongitude());
        } else {
            stmt.setNull(7, java.sql.Types.FLOAT);
            stmt.setNull(8, java.sql.Types.FLOAT);
        }
        if (status.getPlace() != null) {
            stmt.setString(9, status.getPlace().getName());
        } else {
            stmt.setNull(9, java.sql.Types.VARCHAR);
        }
        stmt.setNull(10, java.sql.Types.VARCHAR);
        stmt.executeUpdate();
        stmt.clearParameters();
        stmt.clearBatch();
        stmt.close();
        stmt = null;
        status = null;
    }
}
