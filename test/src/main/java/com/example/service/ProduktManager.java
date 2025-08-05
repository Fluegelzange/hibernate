package com.example.service;

import java.util.List;
import java.util.Properties;

import com.example.model.Category;
import com.example.model.Produkt;

public interface ProduktManager {
    void init(Properties properties);
    Produkt getProduktByProduktNummer(String produktNummer);
    void finish();
    List<Produkt> getProdukteByTitelPattern(String titel);
    Category getCategoryTree();
}
