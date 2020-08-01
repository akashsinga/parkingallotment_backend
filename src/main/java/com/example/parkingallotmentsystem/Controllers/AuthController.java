package com.example.parkingallotmentsystem.Controllers;

import com.example.parkingallotmentsystem.DTO.*;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping
public class AuthController
{
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest)
    {
        Response response=new Response();
        try
        {
            response.setResponse(authService.register(registerRequest));
        } catch (Exception e) {
            response.setResponse(e.getMessage());
            return new ResponseEntity<Response>(response,HttpStatus.OK);
        }
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @PostMapping("/register/checkuser")
    public ResponseEntity<Response> checkUser(@RequestBody String username) throws Exception
    {
        System.out.println(username);
        Response response=new Response();
        response.setResponse(String.valueOf(authService.userExists(username)));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @PostMapping("/register/checkemail")
    public ResponseEntity checkEmail(@RequestBody String email)
    {
        Response response=new Response();
        response.setResponse(String.valueOf(authService.emailExists(email)));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
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

    @PostMapping("/forgotpassword")
    public ResponseEntity<Response> sendVerificationCode(@RequestBody String email)
    {
        Response response=new Response();
        response.setResponse(authService.sendVerificationCode(email));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PostMapping("/forgotpassword/verify")
    public ResponseEntity<Response> verifyCode(@RequestBody VerifyCode verifyCode)
    {
        Response response=new Response();
        response.setResponse(String.valueOf(authService.verifyCode(verifyCode)));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PostMapping("/forgotpassword/reset")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPassword resetPassword)
    {
        Response response=new Response();
        response.setResponse(authService.resetPassword(resetPassword));
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}