// src/main/java/com/grokking/section2_declarativequerying/HibernateUtil.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_2_1_declarative_querying;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * THE DATABASE CONTEXT (Infrastructure Layer):
 * * TEACHING NOTE:
 * This acts as the bridge between our Java code and the database engine.
 * By isolating it here, our User.java model stays clean. If we swap SQLite 
 * for PostgreSQL, we only change the properties in this one file.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {

            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            Configuration config = new Configuration();
            
            // Programmatic Configuration (No messy XML files required!)
            config.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            config.setProperty("hibernate.connection.url", "jdbc:sqlite:orm_demo.db");
            config.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
            
            // Tell Hibernate to automatically drop and recreate tables on startup for our demo
            config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            
            // Register our Domain Model
            config.addAnnotatedClass(User.class);

            return config.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}