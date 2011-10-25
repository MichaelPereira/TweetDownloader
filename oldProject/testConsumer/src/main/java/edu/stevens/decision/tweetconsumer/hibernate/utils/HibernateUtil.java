/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stevens.decision.tweetconsumer.hibernate.utils;


import java.io.File;
import java.net.URL;
import java.util.Properties;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Michael
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
 private static final SessionFactory sessionFactory2;
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            sessionFactory2 = new AnnotationConfiguration().configure("hibernate.cfg_1.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static SessionFactory getSessionFactory2() {
        return sessionFactory2;
    }
}
