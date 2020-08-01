package com.example.parkingallotmentsystem.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="password_resets")
public class PasswordResets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String reset_code;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    @Column
    private String status;
    @Column
    private LocalDateTime createdDateTime;

    public PasswordResets() {
    }

    public PasswordResets(String reset_code, User user, String status,LocalDateTime createdDateTime) {
        this.reset_code = reset_code;
        this.user = user;
        this.status = status;
        this.createdDateTime=createdDateTime;
    }

    public int getId() {
        return id;
    }

    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
