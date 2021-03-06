package com.example.parkingallotmentsystem.Services;

import com.example.parkingallotmentsystem.Models.Booking;
import com.example.parkingallotmentsystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService
{
    @Autowired
    private JavaMailSender javaMailSender;

    static private String fromemail="parkingallotment@gmail.com";
    public void sendRegistrationEmail(User user)
    {
        String mailText=" Dear "+user.getFullname()+",\n\n Thank you for registering with MyParking.\n Your Registration is now confirmed. \n\n Logon to www.myparking.com to make your first parking reservation.\n\n Thanks and Regards,\n MyParking Team";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setFrom(fromemail);
        email.setSubject("Welcome To MyParking");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendBookingSuccessful(Booking booking)
    {
        String mailText=" Dear "+booking.getUser().getFullname()+",\n\n Thank you for using our services.\n Your Parking Reservation with Booking Id: "+booking.getId()+" and Payment ID: "+booking.getPayment_Id()+" is successful.\n Logon to www.myparking.com to download your parking receipt from MyBookings page.\n\n Thanks and Regards,\n MyParking Team.";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(booking.getUser().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Reservation Successful");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendReservationToOwner(Booking booking)
    {
        String mailText=" Dear "+booking.getLocation().getOwner().getName()+",\n\n Your Parking Lot at "+booking.getLocation().getName()
                +" is reserved by "+booking.getUser().getFullname()+" from "+booking.getFromdatetime()+" to "+booking.getTodatetime()+".\n\n Thanks and Regards,\n MyParking Team.";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(booking.getLocation().getOwner().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Lot Reserved");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendResetCode(User user, String verification_code) {
        String mailText=" Dear "+user.getFullname()+",\n\n Your password reset request is verified. Please visit the site with verification code: "+verification_code+" to reset your password.\n\n Thanks and Regards,\n MyParking Team";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setFrom(fromemail);
        email.setSubject("Password Reset Request");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendResetPasswordConfirmation(User user)
    {
        String mailText=" Dear "+user.getFullname()+",\n\n Your password has been successfully reset. \n\n Thanks and Regards,\n MyParking Team";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setFrom(fromemail);
        email.setSubject("Password Reset Successfully");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendCancellationEmail(Booking booking) {
        String mailText=" Dear "+booking.getUser().getFullname()+", \n\n Your parking reservation by ID: "+booking.getId()+" at "+booking.getLocation().getName()+" is cancelled as per your request. \n Refund Initiated. \n\n Thanks and Regards,\n MyParking Team.";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(booking.getUser().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Reservation Cancelled");
        email.setText(mailText);
        javaMailSender.send(email);
        this.sendCancellationToOwner(booking);
    }

    public void sendCancellationToOwner(Booking booking)
    {
        String mailText=" Dear "+booking.getLocation().getOwner().getName()+",\n\n Parking Reservation at "+booking.getLocation().getName()
                +" reserved by "+booking.getUser().getFullname()+" from "+booking.getFromdatetime()+" to "+booking.getTodatetime()+" is cancelled.\n\n Thanks and Regards,\n MyParking Team.";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(booking.getLocation().getOwner().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Reservation Cancelled");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void confirmWaitingListCustomer(Booking booking) {
        String mailText="Dear "+booking.getUser().getFullname()+", \n\n Your parking reservation by ID: "+booking.getId()+" and Payment ID: "+booking.getPayment_Id()+" at "+booking.getLocation().getName()+" is now confirmed. \n Logon to www.myparking.com to download your parking receipt from MyBookings page. \n\n Thanks and Regards,\n MyParking Team";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(booking.getUser().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Reservation Confirmed");
        email.setText(mailText);
        javaMailSender.send(email);
    }

    public void sendCancellationEmailAdmin(Booking temp) {
        String mailText=" Dear "+temp.getUser().getFullname()+", \n\n Your parking reservation by ID: "+temp.getId()+" at "+temp.getLocation().getName()+" is cancelled due to some issue. \n Refund Initiated. \n\n Thanks and Regards,\n MyParking Team.";
        SimpleMailMessage email=new SimpleMailMessage();
        email.setTo(temp.getUser().getEmail());
        email.setFrom(fromemail);
        email.setSubject("Parking Reservation Cancelled");
        email.setText(mailText);
        javaMailSender.send(email);
        this.sendCancellationToOwner(temp);
    }
}
