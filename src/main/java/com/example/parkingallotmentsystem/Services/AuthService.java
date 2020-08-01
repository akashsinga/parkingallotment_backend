package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.LoginRequest;
import com.example.parkingallotmentsystem.DTO.LoginResponse;
import com.example.parkingallotmentsystem.DTO.RegisterRequest;
import com.example.parkingallotmentsystem.DTO.Response;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public String register(RegisterRequest registerRequest) throws Exception
    {
        try
        {
            String password = encryptPassword(registerRequest.getPassword());
            User user = new User(registerRequest.getUsername(),registerRequest.getFullname(),registerRequest.getMobile(),registerRequest.getEmail(),password,"user");
            userRepository.save(user);
            emailService.sendRegistrationEmail(user);
            return "Registration Successful";
        }
        catch(MailException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            return (e.getMessage());
        }
        return "Registration Failed";
    }

    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        if (userRepository.findByUsername(loginRequest.getUsername()).isPresent()) {
            User user = userRepository.getByUsername(loginRequest.getUsername());
            if (new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                loginResponse.setId(user.getId());
                loginResponse.setType(user.getType());
                loginResponse.setUsername(user.getFullname());
            } else {
                throw new Exception("Invalid Credentials");
            }
        } else {
            throw new Exception("user " + loginRequest.getUsername() + " doesnot exist");
        }
        return loginResponse;
    }

    public boolean userExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }
}
          