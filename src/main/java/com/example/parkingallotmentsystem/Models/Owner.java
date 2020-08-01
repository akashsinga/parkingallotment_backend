package com.example.parkingallotmentsystem.Models;

import javax.persistence.*;

@Entity
@Table(name="parking_owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column(unique = true)
    private String email;
    @Column
    private String mobile;

    public Owner() {
    }

    public Owner(String owner_name, String owner_email, String owner_mobile) {
        this.name = owner_name;
        this.email = owner_email;
        this.mobile = owner_mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
