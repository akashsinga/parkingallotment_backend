package com.example.parkingallotmentsystem.DTO;

import java.time.LocalDateTime;

public class GetReports {
    private LocalDateTime fromdatetime;
    private LocalDateTime todatetime;

    public GetReports(LocalDateTime fromdatetime, LocalDateTime todatetime) {
        this.fromdatetime = fromdatetime;
        this.todatetime = todatetime;
    }

    public GetReports() {
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
}
