package com.hms.entity;
import jakarta.validation.constraints.Min;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name="type",nullable = false)
    private String type;

    @Min(value = 1, message = "Room count must be at least 1")
    @Column(name="count",nullable = false)
    private int count;

    @Column(name="date",nullable = false)
    private LocalDate date;

    private double perNightPrice;

    @Version
    private long versions;

    public long getVersions() {
        return versions;
    }

    public void setVersions(long versions) {
        this.versions = versions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPerNightPrice() {
        return perNightPrice;
    }

    public void setPerNightPrice(double perNightPrice) {
        this.perNightPrice = perNightPrice;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
