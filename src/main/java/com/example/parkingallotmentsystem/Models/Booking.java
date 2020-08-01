package com.example.parkingallotmentsystem.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name="bookings")
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="location_id")
    private Location location;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime booking_date;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fromdatetime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime todatetime;

    @Column
    private String status;

    @Column
    private double cost;

    @Column
    private String payment_Id;

    public String getPayment_Id() {
        return payment_Id;
    }

    public void setPayment_Id(String payment_Id) {
        this.payment_Id = payment_Id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getFromdatetime() {
        return fromdatetime;
    }

    public void setFromdatetime(LocalDateTime fromdatetime) {
        this.fromdatetime = fromdatetime;
    }

    public LocalDateTime getTodatetime() {
        return todatetime;
    }

    public void setTodatetime(LocalDateTime todatetime) {
        this.todatetime = todatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(LocalDateTime booking_date) {
        this.booking_date = booking_date;
    }

    public Booking(User user, Location location, LocalDateTime booking_date, LocalDateTime fromdatetime, LocalDateTime todatetime, String status, double cost, String payment_Id)
    {
        this.user = user;
        this.location = location;
        this.booking_date = booking_date;
        this.fromdatetime = fromdatetime;
        this.todatetime = todatetime;
        this.status = status;
        this.cost = cost;
        this.payment_Id = payment_Id;
    }

    public Booking() {
    }
}
