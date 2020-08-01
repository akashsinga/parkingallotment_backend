package com.example.parkingallotmentsystem.DTO;

import java.time.LocalDateTime;

public class ReserveParking
{
    private int user_id,parking_id;
    private double cost;
    private String paymentId,status;
    private LocalDateTime fromdatetime,todatetime;

    public ReserveParking() {
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
