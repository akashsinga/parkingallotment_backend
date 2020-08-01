package com.example.parkingallotmentsystem.DTO;

import java.time.LocalDateTime;

public class CheckAvailability
{
    private LocalDateTime fromdatetime,todatetime;
    private int parking_id;

    public LocalDateTime getFromdatetime() {
        return fromdatetime;
    }

    public CheckAvailability() {
    }


    public LocalDateTime getTodatetime() {
        return todatetime;
    }

    public void setTodatetime(LocalDateTime todatetime) {
        this.todatetime = todatetime;
    }

    public void setFromdatetime(LocalDateTime fromdatetime) {
        this.fromdatetime = fromdatetime;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }
}
