package com.example.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "kategorie")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategorie_id")
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "oberkategorie_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> children = new ArrayList<>();

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getParent() { return parent; }
    public void setParent(Category parent) { this.parent = parent; }

    public List<Category> getChildren() { return children; }
    public void setChildren(List<Category> children) { this.children = children; }

    public void addChild(Category child) {
        child.setParent(this);
        this.children.add(child);
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("📁 ").append(name).append(" (ID: ").append(id).append(")\n");
        for (Category child : children) {
            sb.append(child.toString(prefix + "    "));
        }
        return sb.toString();
    }
}
