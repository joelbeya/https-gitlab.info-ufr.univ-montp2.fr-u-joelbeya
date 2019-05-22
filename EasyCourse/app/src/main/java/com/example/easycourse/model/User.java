package com.example.easycourse.model;

public class User {
    private String role;
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private String token;

    public String getRole() {
        return role;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname= firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setToken(String token) {
//        this.token = token;
//    }
}

