package com.cara.dto;

public class RegisterRequest {
    
    private String username;
    
    private String email;
    
    private String password;
    
    private String firstName;
    
    private String lastName;
    
    private String phoneNumber;
    
    private String address;
    
    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String username, String email, String password, String firstName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}





