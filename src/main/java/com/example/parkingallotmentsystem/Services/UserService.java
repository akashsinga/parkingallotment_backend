package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.CheckAvailability;
import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.BookingRepository;
import com.example.parkingallotmentsystem.Repositories.ParkingLocationRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Location> getLocations()
    {
        return parkingLocationRepository.findAll();
    }

    public User getUserById(int user_id)
    {
        return userRepository.getById(user_id);
    }

    public Location getLocation(int id) {
        return parkingLocationRepository.findById(id).get();
    }

    public void reserveParking(Booking booking) {
        bookingRepository.save(booking);
        if (booking.getStatus().equals("reserved")) {
            emailService.sendBookingSuccessful(booking);
            emailService.sendReservationToOwner(booking);
        }
    }

    public List<Booking> getPreviousBookings(int id) {
        return bookingRepository.getPreviousBookings(id);
    }

    public boolean isOccupied(CheckAvailability checkAvailability)
    {
        return bookingRepository.isAvailable(checkAvailability.getFromdatetime(), checkAvailability.getTodatetime(),checkAvailability.getParking_id())!=0;
    }

    public String cancelReservation(int id) {
        Booking booking=bookingRepository.findById(id).get();
        booking.setStatus("cancelled");
        bookingRepository.save(booking);
        emailService.sendCancellationEmail(booking);

        Booking temp=bookingRepository.getWaitingListReservation(booking.getLocation().getId(),booking.getFromdatetime(),"waiting");
        if(temp==null) {
        }
        else
        {
            System.out.println(temp.getUser().getFullname());
            temp.setStatus("reserved");
            bookingRepository.save(temp);
            emailService.confirmWaitingListCustomer(temp);
            emailService.sendReservationToOwner(temp);
        }
        return "Booking Successfully Cancelled";
    }
}
