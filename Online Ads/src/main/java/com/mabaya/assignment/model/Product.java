package com.mabaya.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "serial_number")
    private UUID serialNumber;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "price", nullable = false)
    private double price;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Campaign> campaign;

    public Product(String title, String category, double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public Product(){}

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
