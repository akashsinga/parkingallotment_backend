package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.GetReports;
import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.Owner;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.BookingRepository;
import com.example.parkingallotmentsystem.Repositories.OwnerRepository;
import com.example.parkingallotmentsystem.Repositories.ParkingLocationRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminService.class);
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
        logger.info("adding a parking lot");
        Owner owner=ownerRepository.getByEmail(location.getOwner().getEmail());
        if(owner==null)
        {
            owner=new Owner(location.getOwner().getName(),location.getOwner().getEmail(),location.getOwner().getMobile());
        }
        Location temp=new Location(location.getName(),location.getLatitude(),location.getLongitude(),location.getPrice_per_hour(),"active",owner);
        parkingLocationRepository.save(temp);
        logger.info("Parking Lot Added Successfully");
        return "Parking Location Added Successfully";
    }

    public long getUserCount()
    {
        logger.info("Fetching User Count");
        return userRepository.getUserCount();
    }

    public List<Location> getLocations()
    {
        logger.info("Fetching All Locations whose status is active");
        return parkingLocationRepository.findAll();
    }

    public long getParkingsCount()
    {
        logger.info("Fetching Total Count of Parking Lots");
        return  parkingLocationRepository.count();
    }

    public long getBookingsCount()
    {
        logger.info("Fetching Total Count of bookings");
        return bookingRepository.count();
    }

    public List<Booking> getBookings()
    {
        logger.info("Fetching List of Bookings");
        return bookingRepository.findAll();
    }

    public String deleteParkingLot(int id)
    {
        logger.info("Delete Parking Lot process started");
        Location location=parkingLocationRepository.getOne(id);
        location.setStatus("inactive");
        parkingLocationRepository.save(location);
        logger.info("Parking Lot Deleted Successfully");
        return "Parking Lot Deleted Successfully";
    }

    public String editParkingLot(int id, Location location)
    {
        logger.info("Edit Parking Lot Started");
        Location location1=parkingLocationRepository.getOne(id);
        location1.setPrice_per_hour(location.getPrice_per_hour());
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        location1.setName(location.getName());
        parkingLocationRepository.save(location1);
        logger.info("Parking Lot Updated Successfully");
        return "Parking Lot Updated Successfully";
    }

    public List<User> getUsers()
    {
        logger.info("Fetching List of Users");
        return userRepository.findAll();
    }

    public String editUser(int id, String role)
    {
        logger.info("Getting User with id:"+id);
        User user=userRepository.getById(id);
        user.setType(role);
        userRepository.save(user);
        logger.info("User Role Modified Successfully");
        return "User Role Modified";
    }

    public List<Booking> getReports(GetReports getReports)
    {
        logger.info("Fetching Bookings in Time Range: "+getReports.getFromdatetime()+" - "+getReports.getTodatetime());
        return bookingRepository.generateReports(getReports.getFromdatetime(),getReports.getTodatetime());
    }
}
