package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.*;
import com.example.parkingallotmentsystem.Models.PasswordResets;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.PasswordResetRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetRepository passwordResetRepository;

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

    public boolean emailExists(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return true;
        }
        return false;
    }

    public String sendVerificationCode(String email) {
        String verification_code=String.format("%06d",new Random().nextInt(999999));
        User user=userRepository.findByEmail(email).get();
        if(user==null)
        {
            return "User Doesnot Exist";
        }
        PasswordResets passwordResets=new PasswordResets(verification_code,user,"code_sent", LocalDateTime.now());
        passwordResetRepository.save(passwordResets);
        emailService.sendResetCode(user,verification_code);
        return "Verification Code Sent";
    }

    public boolean verifyCode(VerifyCode verifyCode)
    {
        User user=userRepository.findByEmail(verifyCode.getEmail()).get();
        PasswordResets passwordResets=passwordResetRepository.getByUserID(user.getId());
        if(passwordResets.getReset_code().equals(verifyCode.getCode()))
        {
            return true;
        }
        return false;
    }

    public String resetPassword(ResetPassword resetPassword)
    {
        User user=userRepository.findByEmail(resetPassword.getEmail()).get();
        String crypted_password=encryptPassword(resetPassword.getPassword());
        user.setPassword(crypted_password);
        userRepository.save(user);
        PasswordResets passwordResets=passwordResetRepository.getByUserID(user.getId());
        passwordResets.setStatus("reset");
        emailService.sendResetPasswordConfirmation(user);
        return "Password Reset Successfully";
    }
}
          