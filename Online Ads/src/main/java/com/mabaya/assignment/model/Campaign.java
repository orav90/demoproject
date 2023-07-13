package com.mabaya.assignment.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_product",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "bid", nullable = false)
    private double bid;

    @Column(name = "status", length = 32, columnDefinition = "varchar(32) default 'ACTIVE'", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CampaignStatus status = CampaignStatus.ACTIVE;

    public Campaign(LocalDateTime startDate, Set<Product> products, String name, double bid) {
        this.startDate = startDate;
        this.products = products;
        this.name = name;
        this.bid = bid;
    }

    public Campaign(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Campaign{");
        sb.append("id=").append(id);
        sb.append(", startDate=").append(startDate);
        sb.append(", products=").append(products);
        sb.append(", name='").append(name).append('\'');
        sb.append(", bid=").append(bid);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
