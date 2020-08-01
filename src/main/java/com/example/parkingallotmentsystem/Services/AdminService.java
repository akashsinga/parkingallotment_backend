package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.Owner;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.BookingRepository;
import com.example.parkingallotmentsystem.Repositories.OwnerRepository;
import com.example.parkingallotmentsystem.Repositories.ParkingLocationRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public String addParkingLot(Location location)
    {
        Owner owner=ownerRepository.getByEmail(location.getOwner().getEmail());
        if(owner==null)
        {
            owner=new Owner(location.getOwner().getName(),location.getOwner().getEmail(),location.getOwner().getMobile());
        }
        Location temp=new Location(location.getName(),location.getLatitude(),location.getLongitude(),location.getPrice_per_hour(),"active",owner);
        parkingLocationRepository.save(temp);
        return "Parking Location Added Successfully";
    }

    public long getUserCount()
    {
        return userRepository.getUserCount();
    }

    public List<Location> getLocations()
    {
        return parkingLocationRepository.findAll();
    }

    public long getParkingsCount() { return  parkingLocationRepository.count(); }

    public long getBookingsCount() {
        return bookingRepository.count();
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public String deleteParkingLot(int id) {
        Location location=parkingLocationRepository.getOne(id);
        location.setStatus("inactive");
        parkingLocationRepository.save(location);
        return "Parking Lot Deleted Successfully";
    }

    public String editParkingLot(int id, Location location) {
        Location location1=parkingLocationRepository.getOne(id);
        location1.setPrice_per_hour(location.getPrice_per_hour());
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        location1.setName(location.getName());
        parkingLocationRepository.save(location1);
        return "Parking Lot Updated Successfully";
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String editUser(int id, String role) {
        User user=userRepository.getById(id);
        user.setType(role);
        userRepository.save(user);
        return "User Role Modified";
    }
}
