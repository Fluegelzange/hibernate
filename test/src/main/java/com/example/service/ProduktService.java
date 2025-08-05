package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.example.model.Category;
import com.example.model.Produkt;

public class ProduktService implements ProduktManager {

    private SessionFactory sessionFactory;

    @Override
    public void init(Properties properties) {
        Configuration config = new Configuration();
        config.setProperties(properties);
        config.addAnnotatedClass(Produkt.class);
        config.addAnnotatedClass(Category.class);
        sessionFactory = config.buildSessionFactory();
    }

    @Override
    public void finish() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public Produkt getProduktByProduktNummer(String produktNummer) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Produkt.class, produktNummer);
        }
    }

    @Override
    public List<Produkt> getProdukteByTitelPattern(String pattern) {
        try (Session session = sessionFactory.openSession()) {
            String hql = (pattern == null || pattern.isBlank())
                    ? "FROM Produkt"
                    : "FROM Produkt WHERE lower(titel) LIKE :pattern";
            Query<Produkt> query = session.createQuery(hql, Produkt.class);

            if (pattern != null && !pattern.isBlank()) {
                query.setParameter("pattern", pattern.toLowerCase().replace('*', '%'));
            }

            return query.list();
        }
    }

    @Override
    public Category getCategoryTree() {
        Session session = sessionFactory.openSession();
    
        Query<Object[]> query = session.createQuery(
            "SELECT c.id, c.name, c.parent.id FROM Category c",
            Object[].class
        );
        List<Object[]> results = query.list();
        session.close();
    
        Map<Integer, Category> idToCategory = new HashMap<>();
        Category root = new Category(-1, "Alle Kategorien"); // Virtueller Wurzelknoten
    
        // Kategorien erstellen
        for (Object[] row : results) {
            int id = (Integer) row[0];
            String name = (String) row[1];
            idToCategory.put(id, new Category(id, name));
        }
    
        // Beziehungen setzen
        for (Object[] row : results) {
            int id = (Integer) row[0];
            Integer parentId = (Integer) row[2];
        
            Category current = idToCategory.get(id);
        
            if (parentId == null) {
                root.addChild(current);
            } else {
                Category parent = idToCategory.get(parentId);
                parent.addChild(current);
            }
        }
    
        return root;
    }


}
