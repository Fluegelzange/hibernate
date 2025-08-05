package com.example;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.model.Produkt;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static void init(Properties properties) {
    try {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Produkt.class); // evtl. weitere Klassen hinzufügen
        sessionFactory = configuration.buildSessionFactory();
    } catch (Throwable ex) {
        throw new ExceptionInInitializerError(ex);
    }
}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
