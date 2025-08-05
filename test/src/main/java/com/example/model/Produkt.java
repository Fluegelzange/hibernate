package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produkt")
public class Produkt {

    @Id
    @Column(name = "produkt_nummer")
    private String produktNummer;  // Das sollte exakt mit dem Feldnamen in der DB übereinstimmen

    @Column(name = "titel")
    private String titel;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "verkaufsrang")
    private Integer verkaufsrang;

    @Column(name = "bild_url")
    private String bildUrl;

    // Getter und Setter
    public String getProduktNummer() {
        return produktNummer;
    }

    public void setProduktNummer(String produktNummer) {
        this.produktNummer = produktNummer;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVerkaufsrang() {
        return verkaufsrang;
    }

    public void setVerkaufsrang(Integer verkaufsrang) {
        this.verkaufsrang = verkaufsrang;
    }

    public String getBildUrl() {
        return bildUrl;
    }

    public void setBildUrl(String bildUrl) {
        this.bildUrl = bildUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "produktNummer='" + produktNummer + '\'' +
                ", titel='" + titel + '\'' +
                ", rating=" + rating +
                ", verkaufsrang=" + verkaufsrang +
                ", bildUrl='" + bildUrl + '\'' +
                '}';
    }
}
