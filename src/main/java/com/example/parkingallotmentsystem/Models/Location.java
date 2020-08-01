package com.example.parkingallotmentsystem.Models;

import javax.persistence.*;

@Entity
@Table(name = "parking_lots")
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private double price_per_hour;
    @Column
    private String status;
    @Column
    private String latitude;
    @Column
    private String longitude;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="owner_id")
    private Owner owner;

    public Location() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location(String name,String latitude, String longitude, double price_per_hour, String status,Owner owner) {
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.price_per_hour = price_per_hour;
        this.status = status;
        this.owner=owner;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
