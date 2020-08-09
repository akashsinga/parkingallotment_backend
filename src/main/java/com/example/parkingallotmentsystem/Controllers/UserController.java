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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/user",headers = {"request_id"})
@Validated
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/reserve")
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

    @GetMapping("/dashboard/getLocations")
    public List<Location> getParkings()
    {
        List<Location> parkings=userService.getLocations();
        return parkings;
    }

    @GetMapping("/bookings/{id}")
    public List<Booking> getPreviousBookings(@PathVariable int id)
    {
        List<Booking> bookings=userService.getPreviousBookings(id);
        return bookings;
    }

    @PostMapping("/parkings/lots/available")
    public ResponseEntity<Response> isAvailable(@RequestBody CheckAvailability checkAvailability) {
        Response response = new Response();
        response.setResponse(String.valueOf(userService.isOccupied(checkAvailability)));
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/bookings/cancel/{id}")
    public ResponseEntity<Response> cancelReservation(@PathVariable int id)
    {
        Response response=new Response();
        response.setResponse(userService.cancelReservation(id));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}
