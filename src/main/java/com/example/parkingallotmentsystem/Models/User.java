package com.example.parkingallotmentsystem.Models;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    @Column
    private String fullname;
    @Column
    private String mobile;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String type;

    public User() {
    }

    public User(String username, String fullname, String mobile, String email, String password, String type) {
        this.username = username;
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
}
        