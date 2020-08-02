package com.example.parkingallotmentsystem.Controllers;

import com.example.parkingallotmentsystem.DTO.CheckAvailability;
import com.example.parkingallotmentsystem.DTO.ReserveParking;
import com.example.parkingallotmentsystem.DTO.Response;
import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/user/reserve")
    public ResponseEntity<Response> reserveParking(@RequestBody ReserveParking reserveParking)
    {
        User user=userService.getUserById(reserveParking.getUser_id());
        Location location=userService.getLocation(reserveParking.getParking_id());

        Booking booking=new Booking(user,location,LocalDateTime.now(),reserveParking.getFromdatetime(),reserveParking.getTodatetime(),reserveParking.getStatus(),reserveParking.getCost(),reserveParking.getPaymentId());
        userService.reserveParking(booking);
        Response response=new Response();
        response.setResponse("Reservation Successful");

        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @GetMapping("/user/dashboard/getLocations")
    public ResponseEntity<List> getParkings()
    {
        List<Location> parkings=userService.getLocations();
        return new ResponseEntity<List>(parkings, HttpStatus.OK);
    }

    @GetMapping("/user/bookings/{id}")
    public ResponseEntity<List> getPreviousBookings(@PathVariable int id)
    {
        List<Booking> bookings=userService.getPreviousBookings(id);
        return new ResponseEntity<List>(bookings,HttpStatus.OK);
    }

    @PostMapping("/user/parkings/lots/available")
    public ResponseEntity<Response> isAvailable(@RequestBody CheckAvailability checkAvailability) {
        Response response = new Response();
        response.setResponse(String.valueOf(userService.isOccupied(checkAvailability)));
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/user/bookings/cancel/{id}")
    public ResponseEntity<Response> cancelReservation(@PathVariable int id)
    {
        Response response=new Response();
        response.setResponse(userService.cancelReservation(id));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}
