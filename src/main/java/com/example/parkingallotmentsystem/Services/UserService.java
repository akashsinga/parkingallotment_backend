package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.CheckAvailability;
import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.BookingRepository;
import com.example.parkingallotmentsystem.Repositories.ParkingLocationRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Location> getLocations()
    {
        logger.info("Fetching Parking Lots");
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
        logger.info("Parking Lot Reserved");
        bookingRepository.save(booking);
        if (booking.getStatus().equals("reserved")) {
            emailService.sendBookingSuccessful(booking);
            emailService.sendReservationToOwner(booking);
        }
    }

    public List<Booking> getPreviousBookings(int id) {
        logger.info("Fetching Previous Bookings");
        return bookingRepository.getPreviousBookings(id);
    }

    public boolean isOccupied(CheckAvailability checkAvailability)
    {
        logger.info("Checking Parking Lot Status");
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
