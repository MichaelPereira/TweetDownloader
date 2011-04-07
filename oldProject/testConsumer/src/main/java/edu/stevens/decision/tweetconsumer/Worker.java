/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stevens.decision.tweetconsumer;

import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import twitter4j.Status;

/**
 *
 * @author michaelpereira
 */
public class Worker implements Runnable {

    Delivery _delivery;
    Connection _mysqlCon;
    String _sqlStatement;

    Worker(Delivery delivery, Connection mysqlCon, String sqlStatement) {
        _delivery = delivery;
        _mysqlCon = mysqlCon;
        _sqlStatement = sqlStatement;
    }

    public void run() {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(_delivery.getBody());
            ois = new ObjectInputStream(bais);
            Status status = (Status) ois.readObject();
            //System.out.println(" [x] Received " + status.getText());
            saveToDatabase(_mysqlCon, _sqlStatement, status);
            ois.close();
            bais.close();
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + " - "+ ex.getLocalizedMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveToDatabase(java.sql.Connection mysqlCon, String sql, Status status) throws SQLException {
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
