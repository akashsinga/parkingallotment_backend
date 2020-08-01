package com.example.parkingallotmentsystem.DTO;

import com.example.parkingallotmentsystem.Models.Booking;

import java.util.List;

public class AdminDashboardDetails
{
    private long user_count,parkings_count,bookings_count;
    private List<Booking> bookings;

    public AdminDashboardDetails() {
    }

    public AdminDashboardDetails(long user_count, long parkings_count, long bookings_count, List<Booking> bookings) {
        this.user_count = user_count;
        this.parkings_count = parkings_count;
        this.bookings_count = bookings_count;
        this.bookings = bookings;
    }

    public long getUser_count() {
        return user_count;
    }

    public void setUser_count(long user_count) {
        this.user_count = user_count;
    }

    public long getParkings_count() {
        return parkings_count;
    }

    public void setParkings_count(long parkings_count) {
        this.parkings_count = parkings_count;
    }

    public long getBookings_count() {
        return bookings_count;
    }

    public void setBookings_count(long bookings_count) {
        this.bookings_count = bookings_count;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
