package com.example.parkingallotmentsystem.Controllers;

import com.example.parkingallotmentsystem.DTO.LoginRequest;
import com.example.parkingallotmentsystem.DTO.LoginResponse;
import com.example.parkingallotmentsystem.DTO.RegisterRequest;
import com.example.parkingallotmentsystem.DTO.Response;
import com.example.parkingallotmentsystem.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController
{
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest)
    {
        Response response=new Response();
        try
        {
            response.setResponse(authService.register(registerRequest));
        } catch (Exception e) {
            response.setResponse(e.getMessage());
            return new ResponseEntity(response,HttpStatus.OK);
        }
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @PostMapping("/register/checkuser")
    public ResponseEntity checkUser(@RequestBody String username) throws Exception
    {
        System.out.println(username);
        Response response=new Response();
        response.setResponse(String.valueOf(authService.userExists(username)));
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse;
        try
        {
            loginResponse=authService.login(loginRequest);
        }
        catch (Exception e)
        {
            Response response=new Response();
            response.setResponse(e.getMessage());
            return new ResponseEntity(response,HttpStatus.OK);
        }
        return new ResponseEntity(loginResponse,HttpStatus.OK);
    }
}