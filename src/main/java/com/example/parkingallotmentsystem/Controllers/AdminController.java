package com.example.parkingallotmentsystem.Controllers;

import com.example.parkingallotmentsystem.DTO.AdminDashboardDetails;
import com.example.parkingallotmentsystem.DTO.GetReports;
import com.example.parkingallotmentsystem.DTO.Response;
import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.Location;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Services.AdminService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin",headers = {"request_id"})
@Validated
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @GetMapping("/locations/get")
    public List<Location> getLocations()
    {
        List<Location> locations=adminService.getLocations();
        return locations;
    }
    @GetMapping("/users")
    public List<User> getUsers()
    {
        List<User> users=adminService.getUsers();
        return users;
    }

    @GetMapping("/dashboard/getCounts")
    public ResponseEntity<AdminDashboardDetails> getDashboardCounts()
    {
        AdminDashboardDetails adminDashboardDetails =new AdminDashboardDetails(adminService.getUserCount(),adminService.getParkingsCount(),adminService.getBookingsCount(),adminService.getBookings());
        return new ResponseEntity<AdminDashboardDetails>(adminDashboardDetails,HttpStatus.OK);
    }

    @GetMapping("/parkings/delete/{id}")
    public ResponseEntity<Response> deleteParkingLot(@PathVariable int id)
    {
        Response response=new Response();
        try
        {
            response.setResponse(adminService.deleteParkingLot(id));
        } catch (Exception e) {
            response.setResponse(e.getMessage());
        }
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @GetMapping("/bookings/cancel/{id}")
    public ResponseEntity<Response> cancelReservation(@PathVariable int id)
    {
        Response resp=new Response();
        try{
            resp.setResponse(adminService.cancelReservation(id));
        }
        catch(Exception e)
        {
            resp.setResponse(e.getMessage());
        }
        return new ResponseEntity<Response>(resp,HttpStatus.OK);
    }

    @PostMapping("/parkings/add")
    public ResponseEntity<Response> addParking(@RequestBody Location location)
    {
        Response response=new Response();
        response.setResponse(adminService.addParkingLot(location));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PostMapping("/parkings/edit/{id}")
    public ResponseEntity<Response> editParking(@PathVariable int id,@RequestBody Location location)
    {
        Response response=new Response();
        try
        {
            response.setResponse(adminService.editParkingLot(id,location));
        } catch (Exception e) {
            response.setResponse(e.getMessage());
        }
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PostMapping("/users/edit/{id}")
    public ResponseEntity<Response> editUser(@PathVariable int id,@RequestBody String role)
    {
        Response response=new Response();
        response.setResponse(adminService.editUser(id,role));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PostMapping("/reports")
    public List<Booking> getReports(@RequestBody GetReports getReports)
    {
        List<Booking> bookings=adminService.getReports(getReports);
        System.out.println(getReports.getFromdatetime());
        return bookings;
    }
}
