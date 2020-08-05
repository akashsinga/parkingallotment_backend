package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.DTO.*;
import com.example.parkingallotmentsystem.Models.PasswordResets;
import com.example.parkingallotmentsystem.Models.User;
import com.example.parkingallotmentsystem.Repositories.PasswordResetRepository;
import com.example.parkingallotmentsystem.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
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
            logger.info("Encrypting Password");
            String password = encryptPassword(registerRequest.getPassword());
            User user = new User(registerRequest.getUsername(),registerRequest.getFullname(),registerRequest.getMobile(),registerRequest.getEmail(),password,"user");
            logger.info("User Successfully Registered");
            userRepository.save(user);
            logger.info("Sending Registration Email");
            emailService.sendRegistrationEmail(user);
            return "Registration Successful";
        }
        catch(MailException e)
        {
            logger.info("Sending Mail Failed");
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
        return "Registration Failed";
    }

    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception
    {
        LoginResponse loginResponse = new LoginResponse();
        logger.info("Fetching User By Username");
        if (userRepository.findByUsername(loginRequest.getUsername()).isPresent())
        {
            logger.info("User with username: "+loginRequest.getUsername()+" found");
            User user = userRepository.getByUsername(loginRequest.getUsername());
            if (new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword()))
            {
                logger.info("Login Successful");
                loginResponse.setId(user.getId());
                loginResponse.setType(user.getType());
                loginResponse.setUsername(user.getFullname());
            } else {
                logger.info("Invalid Credentials");
                throw new Exception("Invalid Credentials");
            }
        } else {
            logger.info("User Doesnot Exist");
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

    public String sendVerificationCode(String email)
    {
        logger.info("Creating a Verification Code");
        String verification_code=String.format("%06d",new Random().nextInt(999999));
        User user=userRepository.findByEmail(email).get();
        if(user==null)
        {
            logger.error("User with requested email doesnot exist");
            return "User Doesnot Exist";
        }
        logger.info("Verification Code Sent");
        PasswordResets passwordResets=new PasswordResets(verification_code,user,"code_sent", LocalDateTime.now());
        passwordResetRepository.save(passwordResets);
        emailService.sendResetCode(user,verification_code);
        return "Verification Code Sent";
    }

    public boolean verifyCode(VerifyCode verifyCode)
    {
        logger.info("Verifying Entered Code");
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
        logger.info("Password Reset Initiated");
        User user=userRepository.findByEmail(resetPassword.getEmail()).get();
        String crypted_password=encryptPassword(resetPassword.getPassword());
        user.setPassword(crypted_password);
        userRepository.save(user);
        logger.info("Password Reset Successfully");
        PasswordResets passwordResets=passwordResetRepository.getByUserID(user.getId());
        passwordResets.setStatus("reset");
        logger.info("Sending Password Reset Mail");
        emailService.sendResetPasswordConfirmation(user);
        return "Password Reset Successfully";
    }
}
          