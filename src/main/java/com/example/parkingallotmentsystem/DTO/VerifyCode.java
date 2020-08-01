package com.example.parkingallotmentsystem.DTO;

public class VerifyCode {
    private String email;
    private String code;

    public VerifyCode() {
    }

    public VerifyCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
